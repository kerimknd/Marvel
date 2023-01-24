package com.example.marvel.api
import com.example.marvel.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit



object DBClient {
    fun getClient():DBInterface{
        val requestInterceptor = Interceptor{
                chain ->  val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("apikey", Constants.PUBLIC_KEY)
            .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DBInterface::class.java)

    }
}