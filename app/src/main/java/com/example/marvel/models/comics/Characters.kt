package com.example.marvel.models.comics


import com.google.gson.annotations.SerializedName

data class Characters(
    @SerializedName("available")
    val available: Int,
    @SerializedName("collectionURI")
    val collectionURI: String,
    @SerializedName("items")
    val items: List<İtem>,
    @SerializedName("returned")
    val returned: Int
)