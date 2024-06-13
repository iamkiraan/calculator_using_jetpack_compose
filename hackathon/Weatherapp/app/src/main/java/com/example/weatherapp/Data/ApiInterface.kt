package com.example.weatherapp.Data

import com.example.weatherapp.Data.Models.Containers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather?")
    suspend fun getCurrentWeather(
        @Query("q") city : String,
        @Query("appid") apiKey : String,

    ):Response<Containers>    // containers bhitra banko sabai kotlin data hson class haru


}