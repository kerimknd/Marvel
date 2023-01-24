package com.example.marvel.models.comics


import com.google.gson.annotations.SerializedName

data class İtemX(
    @SerializedName("name")
    val name: String,
    @SerializedName("resourceURI")
    val resourceURI: String,
    @SerializedName("role")
    val role: String
)