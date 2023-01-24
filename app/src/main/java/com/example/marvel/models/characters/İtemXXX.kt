package com.example.marvel.models.characters


import com.google.gson.annotations.SerializedName

data class İtemXXX(
    @SerializedName("name")
    val name: String,
    @SerializedName("resourceURI")
    val resourceURI: String,
    @SerializedName("type")
    val type: String
)