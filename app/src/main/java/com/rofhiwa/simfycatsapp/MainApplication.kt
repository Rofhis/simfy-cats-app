package com.rofhiwa.simfycatsapp

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.rofhiwa.simfycatsapp.di.components.ApplicationComponent
import com.rofhiwa.simfycatsapp.di.components.DaggerApplicationComponent
import com.rofhiwa.simfycatsapp.di.modules.ApplicationModule
import com.rofhiwa.simfycatsapp.di.modules.NetworkModule

class MainApplication : MultiDexApplication() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent
            .builder()
            .application(this)
            .applicationModule(ApplicationModule(this))
            .networkModule(NetworkModule(this))
            .build()
        component.inject(this)

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}