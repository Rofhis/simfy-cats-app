package com.rofhiwa.simfycatsapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rofhiwa.simfycatsapp.annotation.ViewModelKey
import com.rofhiwa.simfycatsapp.di.factory.DaggerViewModelFactory
import com.rofhiwa.simfycatsapp.ui.dashboard.DashboardViewModel
import com.rofhiwa.simfycatsapp.ui.info.CatsInfoViewModel
import com.rofhiwa.simfycatsapp.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

  @Binds
  internal abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

  @Binds
  @IntoMap
  @ViewModelKey(MainViewModel::class)
  abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(DashboardViewModel::class)
  abstract fun bindDashboardViewModel(viewModel: DashboardViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(CatsInfoViewModel::class)
  abstract fun bindCatsInfoViewModel(viewModel: CatsInfoViewModel): ViewModel
}