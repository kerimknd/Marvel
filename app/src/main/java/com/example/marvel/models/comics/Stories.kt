package com.example.marvel.models.comics


import com.google.gson.annotations.SerializedName

data class Stories(
    @SerializedName("available")
    val available: Int,
    @SerializedName("collectionURI")
    val collectionURI: String,
    @SerializedName("items")
    val items: List<İtemXX>,
    @SerializedName("returned")
    val returned: Int
)