package com.example.marvel.utils

import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class Constants {
    companion object {
        const val BASE_URL = "https://gateway.marvel.com/v1/public/"
        val timeStamp = Timestamp(System.currentTimeMillis()).time.toString()
        const val PUBLIC_KEY = "21549a6f1b2152ba40467341976dcef0"
        private const val PRIVATE_KEY = "b62980c311b590ee6a469887ffc9d8829f118f83"
        const val FIRST_PAGE = 30
        const val POST_PER_PAGE = 30
        fun hash(): String{
            val input = "$timeStamp$PRIVATE_KEY$PUBLIC_KEY"
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1,md.digest(input.toByteArray())).toString(16).padStart(32,'0')
        }
    }
}