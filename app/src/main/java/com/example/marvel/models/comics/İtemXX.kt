package com.example.marvel.models.comics


import com.google.gson.annotations.SerializedName

data class İtemXX(
    @SerializedName("name")
    val name: String,
    @SerializedName("resourceURI")
    val resourceURI: String,
    @SerializedName("type")
    val type: String
)