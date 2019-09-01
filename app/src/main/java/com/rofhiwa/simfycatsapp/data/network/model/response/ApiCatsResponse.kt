package com.rofhiwa.simfycatsapp.data.network.model.response

import com.google.gson.annotations.SerializedName

data class ApiCatsResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val imageUrl: String,
    @SerializedName("source_url")
    val sourceUrl: String
)