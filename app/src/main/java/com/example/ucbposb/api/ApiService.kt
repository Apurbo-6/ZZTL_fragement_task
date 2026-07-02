package com.example.ucbposb.api

import com.example.ucbposb.model.HomeResponse
import com.example.ucbposb.model.MenuItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("home.php")
    fun getHome(): Call<HomeResponse>

    @GET("menu.php")
    fun getMenu(): Call<List<MenuItem>>
}