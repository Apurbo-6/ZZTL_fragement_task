package com.example.ucbposb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ucbposb.model.HomeResponse
import com.example.ucbposb.model.MenuItem
import com.example.ucbposb.repository.AppRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SharedViewModel : ViewModel() {

    private val repository = AppRepository()

    // ----------------------------
    // Home Data
    // ----------------------------

    private val _homeData = MutableLiveData<HomeResponse>()
    val homeData: LiveData<HomeResponse> = _homeData

    // ----------------------------
    // Menu Data
    // ----------------------------

    private val _menuData = MutableLiveData<List<MenuItem>>()
    val menuData: LiveData<List<MenuItem>> = _menuData

    // ----------------------------
    // Loading State
    // ----------------------------

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    // ----------------------------
    // Error Message
    // ----------------------------

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // ----------------------------
    // API Status
    // ----------------------------

    private val _homeLoaded = MutableLiveData(false)
    val homeLoaded: LiveData<Boolean> = _homeLoaded

    private val _menuLoaded = MutableLiveData(false)
    val menuLoaded: LiveData<Boolean> = _menuLoaded

    // =====================================================
    // Load Home API
    // =====================================================

    fun loadHome() {

        _isLoading.value = true

        repository.getHome().enqueue(object : Callback<HomeResponse> {

            override fun onResponse(
                call: Call<HomeResponse>,
                response: Response<HomeResponse>
            ) {

                if (response.isSuccessful && response.body() != null) {

                    _homeData.value = response.body()
                    _homeLoaded.value = true

                } else {

                    _error.value = "Unable to load Home data"

                }

                _isLoading.value = false
            }

            override fun onFailure(
                call: Call<HomeResponse>,
                t: Throwable
            ) {

                _error.value = t.message ?: "Unknown Error"
                _isLoading.value = false

            }

        })

    }

    // =====================================================
    // Load Menu API
    // =====================================================

    fun loadMenu() {

        _isLoading.value = true

        repository.getMenu().enqueue(object : Callback<List<MenuItem>> {

            override fun onResponse(
                call: Call<List<MenuItem>>,
                response: Response<List<MenuItem>>
            ) {

                if (response.isSuccessful && response.body() != null) {

                    _menuData.value = response.body()
                    _menuLoaded.value = true

                } else {

                    _error.value = "Unable to load Menu data"

                }

                _isLoading.value = false

            }

            override fun onFailure(
                call: Call<List<MenuItem>>,
                t: Throwable
            ) {

                _error.value = t.message ?: "Unknown Error"
                _isLoading.value = false

            }

        })

    }

}