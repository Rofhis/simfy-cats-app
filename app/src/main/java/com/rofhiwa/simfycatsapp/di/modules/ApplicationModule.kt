package com.rofhiwa.simfycatsapp.di.modules

import android.app.Application
import com.rofhiwa.simfycatsapp.annotation.ForApplication
import com.rofhiwa.simfycatsapp.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class ApplicationModule(private val application: Application) {

  @Provides
  @Singleton
  @ForApplication
  fun provideApplication(): Application {
    return application
  }

  @Singleton
  @Provides
  fun provideDatabaseInstance(): AppDatabase {
    return AppDatabase.getInstance(application)
  }
}