package com.abdulrichard.foodapps.ui.home.module

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdulrichard.foodapps.data.model.home.FoodModel
import com.abdulrichard.foodapps.data.response.home.HomeResponse
import com.abdulrichard.foodapps.network.ApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class HomeViewModel: ViewModel(), CoroutineScope {
    private lateinit var apiService: ApiService

    val onSuccess = MutableLiveData<List<FoodModel>>()
    val onFailure = MutableLiveData<String>()
    val onLoading = MutableLiveData<Boolean>()


    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setHomeService(service: ApiService) {
        this.apiService = service
    }

    fun getListFood() {
        launch {
            onLoading.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    apiService.getFoodList()
                } catch (e: HttpException) {
                    withContext(Dispatchers.Main) {
                        onLoading.value = false
                        when {
                            e.code() == 404 -> {
                                onFailure.value = "Data Not Found"
                            }
                            else -> {
                                onFailure.value = "Unknown error"
                            }
                        }
                    }
                }
            }

            if (response is HomeResponse) {
                onLoading.value = false
                val list = response.meals.map {
                    FoodModel(
                        foodId = it.foodId,
                        foodName = it.foodName,
                        foodImage = it.foodImage
                    )
                }
                onSuccess.value = list
            } else {
                onFailure.value = response.toString()
            }
        }
    }
}