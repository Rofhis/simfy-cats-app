package com.rofhiwa.simfycatsapp.data.network


import com.rofhiwa.simfycatsapp.data.network.model.response.ApiCatsResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("get")
    fun doFetchCatsImages(
        @Query("format") format: String,
        @Query("results_per_page") resultsPerPage: Int,
        @Query("size") size: String,
        @Query("type") type: String
    ): Flowable<MutableList<ApiCatsResponse>>

}