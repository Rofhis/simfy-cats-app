package com.rofhiwa.simfycatsapp.ui.info

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.rofhiwa.simfycatsapp.R
import com.rofhiwa.simfycatsapp.databinding.FragmentCatsInfoBinding
import com.rofhiwa.simfycatsapp.di.components.ApplicationComponent
import com.rofhiwa.simfycatsapp.di.factory.DaggerViewModelFactory
import com.rofhiwa.simfycatsapp.ui.base.BaseFragment
import com.rofhiwa.simfycatsapp.ui.main.MainActivity
import com.rofhiwa.simfycatsapp.utils.GlideApp
import javax.inject.Inject

class CatsInfoFragment : BaseFragment<FragmentCatsInfoBinding, CatsInfoViewModel>() {

  @Inject
  lateinit var viewModeFactory: DaggerViewModelFactory

  private lateinit var catsInfoViewModel: CatsInfoViewModel

  private lateinit var dataBinding: FragmentCatsInfoBinding

  private lateinit var mainActivity: MainActivity

  private lateinit var simpleExoPlayer: SimpleExoPlayer

  private val args: CatsInfoFragmentArgs by navArgs()

  override fun getLayoutId(): Int = R.layout.fragment_cats_info

  override fun getViewModel(): CatsInfoViewModel = catsInfoViewModel

  override fun performDependencyInjection(component: ApplicationComponent) {
    component.inject(this)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mainActivity = requireActivity() as MainActivity
    catsInfoViewModel =
      ViewModelProviders.of(this, viewModeFactory)
        .get(CatsInfoViewModel::class.java)
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

    dataBinding.title.text = args.imageTitle
    dataBinding.description.text = args.imageDescription

    GlideApp.with(this)
      .load(args.imageUrl)
      .centerCrop()
      .placeholder(R.drawable.placeholder)
      .error(R.drawable.placeholder)
      .diskCacheStrategy(DiskCacheStrategy.DATA)
      .into(dataBinding.fullImageView)

    dataBinding.btnPlay.setOnClickListener {

      val thisButton = it as Button

      if (thisButton.text == getString(R.string.lbl_play)) {
        thisButton.text = getString(R.string.lbl_stop)
        thisButton.setBackgroundColor(
          ContextCompat.getColor(
            requireContext(),
            R.color.colorRed
          )
        )
      } else {
        thisButton.text = getString(R.string.lbl_play)
        thisButton.setBackgroundColor(
          ContextCompat.getColor(
            requireContext(),
            R.color.colorPrimary
          )
        )
      }

      simpleExoPlayer.playWhenReady = !simpleExoPlayer.playWhenReady
    }
  }

  override fun onResume() {
    super.onResume()
    initExoPlayer()
  }

  override fun onPause() {
    super.onPause()
    simpleExoPlayer.release()
  }

  private fun initExoPlayer() {
    val trackSelector = DefaultTrackSelector()
    simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
      requireContext(),
      trackSelector
    )
    val userAgent = Util.getUserAgent(
      requireContext(),
      getString(R.string.app_name)
    )

    val audioUri = Uri.parse("asset:///audio/angry-cat-sounds.mp3")
    val dataSourceFactory = DefaultDataSourceFactory(requireContext(), userAgent)
    val mediaSource =
      ProgressiveMediaSource.Factory(dataSourceFactory)
        .createMediaSource(audioUri)

    simpleExoPlayer.prepare(mediaSource)
  }
}
