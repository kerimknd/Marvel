package com.example.marvel.models.characters


import com.google.gson.annotations.SerializedName

data class Comics(
    @SerializedName("available")
    val available: Int,
    @SerializedName("collectionURI")
    val collectionURI: String,
    @SerializedName("items")
    val items: List<İtem>,
    @SerializedName("returned")
    val returned: Int
)