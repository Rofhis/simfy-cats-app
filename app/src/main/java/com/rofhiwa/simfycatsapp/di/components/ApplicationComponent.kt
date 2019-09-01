package com.rofhiwa.simfycatsapp.di.components

import android.app.Application
import com.rofhiwa.simfycatsapp.MainApplication
import com.rofhiwa.simfycatsapp.di.modules.ApplicationModule
import com.rofhiwa.simfycatsapp.di.modules.NetworkModule
import com.rofhiwa.simfycatsapp.ui.dashboard.DashboardFragment
import com.rofhiwa.simfycatsapp.ui.info.CatsInfoFragment
import com.rofhiwa.simfycatsapp.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {
    fun inject(app: MainApplication)
    fun inject(activity: MainActivity)
    fun inject(fragment: DashboardFragment)
    fun inject(fragment: CatsInfoFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(module: Application): Builder

        fun applicationModule(module: ApplicationModule): Builder
        fun networkModule(module: NetworkModule): Builder

        fun build(): ApplicationComponent
    }
}