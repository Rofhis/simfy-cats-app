package com.rofhiwa.simfycatsapp.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

  val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

  override fun onCleared() {
    if (!mCompositeDisposable.isDisposed) {
      mCompositeDisposable.dispose()
    }
    super.onCleared()
  }
}