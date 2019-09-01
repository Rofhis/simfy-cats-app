package com.rofhiwa.simfycatsapp.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rofhiwa.simfycatsapp.R.string
import com.rofhiwa.simfycatsapp.data.db.entity.CatsEntity
import com.rofhiwa.simfycatsapp.databinding.FragmentDashboardBinding
import com.rofhiwa.simfycatsapp.di.components.ApplicationComponent
import com.rofhiwa.simfycatsapp.di.factory.DaggerViewModelFactory
import com.rofhiwa.simfycatsapp.ui.base.BaseFragment
import com.rofhiwa.simfycatsapp.ui.main.MainActivity
import javax.inject.Inject

class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>() {

  @Inject
  lateinit var viewModeFactory: DaggerViewModelFactory

  private lateinit var dashboardViewModel: DashboardViewModel

  private lateinit var dataBinding: FragmentDashboardBinding

  private lateinit var mainActivity: MainActivity

  private lateinit var catsRecyclerViewAdapter: CatsRecyclerViewAdapter

  override fun getLayoutId(): Int = com.rofhiwa.simfycatsapp.R.layout.fragment_dashboard

  override fun getViewModel(): DashboardViewModel = dashboardViewModel

  override fun performDependencyInjection(component: ApplicationComponent) {
    component.inject(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mainActivity = requireActivity() as MainActivity
    dashboardViewModel =
      ViewModelProviders.of(this, viewModeFactory)
          .get(DashboardViewModel::class.java)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    super.onCreateView(inflater, container, savedInstanceState)
    dataBinding = getViewDataBinding()
    return dataBinding.root
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)

    mainActivity.showProgressBar()
    dashboardViewModel.fetchCatsFromLocalDatabase()
    dashboardViewModel.catsListLiveData.observe(this, catsListLiveDataObserver)

    catsRecyclerViewAdapter = CatsRecyclerViewAdapter(mutableListOf())
    catsRecyclerViewAdapter.setHasStableIds(true)

    dataBinding.catsRecyclerView.apply {
      layoutManager = LinearLayoutManager(context)
      setHasFixedSize(true)
      adapter = catsRecyclerViewAdapter
    }
  }

  private val catsListLiveDataObserver = Observer<MutableList<CatsEntity>> { catsList ->
    if (catsList.isNotEmpty()) {
      catsRecyclerViewAdapter.refreshData(catsList)
      mainActivity.hideProgressBar()
    } else {
      fetchNetworkCats()
    }
  }

  private fun fetchNetworkCats() {
    if (isNetworkConnected()) {
      dashboardViewModel.fetchCatsFromServer()
    } else {

      AlertDialog.Builder(requireContext())
          .setMessage(getString(string.msg_network_required))
          .setPositiveButton(getString(string.lbl_open_network)) { dialog, _ ->
            val intent = Intent(Settings.ACTION_SETTINGS)
            startActivityForResult(intent, SETTINGS_REQUEST_CODE)
            dialog.dismiss()
          }
          .setCancelable(false)
          .show()
    }
  }

  override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
  ) {

    when (requestCode) {
      SETTINGS_REQUEST_CODE -> fetchNetworkCats()
    }
  }

  companion object {
    const val SETTINGS_REQUEST_CODE = 400
  }
}
