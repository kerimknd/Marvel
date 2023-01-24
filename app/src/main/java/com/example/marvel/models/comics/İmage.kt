package com.example.marvel.models.comics


import com.google.gson.annotations.SerializedName

data class İmage(
    @SerializedName("extension")
    val extension: String,
    @SerializedName("path")
    val path: String
)