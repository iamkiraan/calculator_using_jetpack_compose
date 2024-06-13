package com.example.programmatic

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("discovery/rest?version=v1")
    fun getData(): Call<doceFiles>
}