package com.rofhiwa.simfycatsapp.di.modules

import android.app.Application
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.rofhiwa.simfycatsapp.BuildConfig
import com.rofhiwa.simfycatsapp.data.network.ApiService
import com.rofhiwa.simfycatsapp.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@Suppress("unused")
class NetworkModule(private val application: Application) {

  @Provides
  @Reusable
  internal fun provideApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
  }

  /**
   * Provides the Retrofit object.
   * @return the Retrofit object
   */
  @Provides
  @Reusable
  internal fun provideRetrofitInterface(): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BuildConfig.BASE_URL)
      .client(getOkHttpClient())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  }

  private fun getOkHttpClient(): OkHttpClient {

    val context = application.applicationContext
    val cacheSize = (5 * 1024 * 1024).toLong()
    val cache = Cache(context.cacheDir, cacheSize)

    return OkHttpClient.Builder()
      .cache(cache)
      .addNetworkInterceptor(StethoInterceptor())
      .addInterceptor { chain ->
        var request = chain.request()
        request = if (NetworkUtils.isNetworkConnected(context)) {
          request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
        } else {
          request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
        }

        chain.proceed(request)
      }
      .build()
  }
}