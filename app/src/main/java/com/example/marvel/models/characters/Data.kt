package com.example.marvel.models.characters


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("count")
    val count: Int,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("results")
    val characterList: List<Character>,
    @SerializedName("total")
    val total: Int
)