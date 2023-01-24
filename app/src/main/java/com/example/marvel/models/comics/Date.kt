package com.example.marvel.models.comics


import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class Date(
    @SerializedName("date")
    val date: String,
    @SerializedName("type")
    val type: String
)