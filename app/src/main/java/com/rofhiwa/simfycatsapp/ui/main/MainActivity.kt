package com.rofhiwa.simfycatsapp.ui.main

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.rofhiwa.simfycatsapp.R
import com.rofhiwa.simfycatsapp.databinding.ActivityMainBinding
import com.rofhiwa.simfycatsapp.extensions.showLongToast
import com.rofhiwa.simfycatsapp.utils.NetworkUtils
import kotlinx.android.synthetic.main.activity_main.nav_host_fragment
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding

  private var doubleBackToExitPressedOnce = false

  override fun onSupportNavigateUp() =
    findNavController(R.id.nav_host_fragment).navigateUp() || super.onSupportNavigateUp()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    setSupportActionBar(binding.mainToolbar)
    NavigationUI.setupActionBarWithNavController(
        this,
        NavHostFragment.findNavController(nav_host_fragment)
    )
  }

  fun showProgressBar() {
    binding.progressBar.visibility = View.VISIBLE
    binding.appMainContent.visibility = View.GONE
  }

  fun hideProgressBar() {
    binding.progressBar.visibility = View.GONE
    binding.appMainContent.visibility = View.VISIBLE
  }

  fun isNetworkConnected(): Boolean {
    return NetworkUtils.isNetworkConnected(applicationContext)
  }

  override fun onBackPressed() {

    val backStackCount = nav_host_fragment.childFragmentManager.backStackEntryCount

    if (backStackCount > 0) {
      super.onBackPressed()
    } else {

      if (doubleBackToExitPressedOnce) {
        finish()
        exitProcess(0)
      }
      this.doubleBackToExitPressedOnce = true
      baseContext?.showLongToast(getString(R.string.msg_tap_to_exit))
      Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 3000)
    }
  }
}

