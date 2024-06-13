package com.example.lolmemes

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retrofitInstances {
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl("https://meme-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    val apiinterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }
}