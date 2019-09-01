package com.rofhiwa.simfycatsapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.rofhiwa.simfycatsapp.MainApplication
import com.rofhiwa.simfycatsapp.di.components.ApplicationComponent
import com.rofhiwa.simfycatsapp.ui.main.MainActivity

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment() {

  private lateinit var mainActivity: MainActivity
  private lateinit var mRootView: View
  private lateinit var mViewDataBinding: T
  private lateinit var mViewModel: V

  /**
   * @return layout resource id
   */
  @LayoutRes
  abstract fun getLayoutId(): Int

  /**
   * Override for set view model
   *
   * @return view model instance
   */
  abstract fun getViewModel(): V

  /**
   * Override for performing dependency injection
   *
   */
  abstract fun performDependencyInjection(component: ApplicationComponent)

  override fun onCreate(savedInstanceState: Bundle?) {
    val component = (requireActivity().applicationContext as MainApplication).component
    performDependencyInjection(component)
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(false)
    mainActivity = (requireActivity() as MainActivity)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    this.mViewModel = getViewModel()
    val v = getLayoutId()
    mViewDataBinding = DataBindingUtil.inflate(inflater, v, container, false)
    mRootView = mViewDataBinding.root
    return mRootView
  }

  fun getViewDataBinding(): T {
    return mViewDataBinding
  }

  fun isNetworkConnected(): Boolean {
    return mainActivity.isNetworkConnected()
  }
}