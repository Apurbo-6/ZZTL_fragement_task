package com.example.ucbposb.repository

import com.example.ucbposb.api.RetrofitClient
import com.example.ucbposb.model.HomeResponse
import com.example.ucbposb.model.MenuItem
import retrofit2.Call

class AppRepository {

    fun getHome(): Call<HomeResponse> {
        return RetrofitClient.api.getHome()
    }

    fun getMenu(): Call<List<MenuItem>> {
        return RetrofitClient.api.getMenu()
    }
}