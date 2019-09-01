package com.rofhiwa.simfycatsapp.data.network.repository

import com.rofhiwa.simfycatsapp.data.network.ApiService
import com.rofhiwa.simfycatsapp.data.network.model.response.ApiCatsResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ApiRepository @Inject constructor() {

    @Inject
    lateinit var apiService: ApiService


    fun fetchCatsFromServer(
        onSuccess: (MutableList<ApiCatsResponse>) -> Unit,
        onError: (Throwable) -> Unit
    ): Disposable {
        return apiService.doFetchCatsImages(
            format = "json",
            resultsPerPage = 100,
            size = "small",
            type = "png"
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result -> onSuccess(result) }, { throwable -> onError(throwable) })
    }
}