package com.rofhiwa.simfycatsapp.ui.dashboard

import androidx.lifecycle.MutableLiveData
import com.rofhiwa.simfycatsapp.data.db.entity.CatsEntity
import com.rofhiwa.simfycatsapp.data.db.repository.CatsRepository
import com.rofhiwa.simfycatsapp.data.network.model.response.ApiCatsResponse
import com.rofhiwa.simfycatsapp.data.network.repository.ApiRepository
import com.rofhiwa.simfycatsapp.ui.base.BaseViewModel
import javax.inject.Inject

class DashboardViewModel @Inject constructor() : BaseViewModel() {

  @Inject
  lateinit var catsRepository: CatsRepository

  @Inject
  lateinit var apiRepository: ApiRepository

  val catsListLiveData: MutableLiveData<MutableList<CatsEntity>> by lazy {
    MutableLiveData<MutableList<CatsEntity>>()
  }

  fun fetchCatsFromLocalDatabase() {
    mCompositeDisposable.add(catsRepository.findByAll(catsListLiveData::postValue, Throwable::printStackTrace))
  }

  fun fetchCatsFromServer() {
    mCompositeDisposable.add(apiRepository.fetchCatsFromServer(this::handleResult, Throwable::printStackTrace))
  }

  private fun handleResult(catsResponseList: MutableList<ApiCatsResponse>) {

    if (catsResponseList.isNotEmpty()) {

      val newCatsEntityList = mutableListOf<CatsEntity>()

      catsResponseList.forEachIndexed { index, cat ->

        val title = "Image ${index + 1}"

        newCatsEntityList.add(
            CatsEntity(
                title = title,
                imageUrl = cat.imageUrl,
                description = "This is the description for $title, It's a really cool image, bask in it's gorgeousness"
            )
        )
      }

      if (newCatsEntityList.isNotEmpty()) {
        mCompositeDisposable.add(catsRepository.insertMany(newCatsEntityList, { isSaved ->
          if (isSaved) {
            fetchCatsFromLocalDatabase()
          }
        }, Throwable::printStackTrace))
      }
    }
  }
}