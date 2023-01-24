package com.example.marvel.models.characters


import com.google.gson.annotations.SerializedName

data class İtem(
    @SerializedName("name")
    val name: String,
    @SerializedName("resourceURI")
    val resourceURI: String
)