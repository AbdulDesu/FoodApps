package com.abdulrichard.foodapps.ui.detail.module

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abdulrichard.foodapps.data.model.detail.FoodDetailModel
import com.abdulrichard.foodapps.data.response.detail.FoodDetailResponse
import com.abdulrichard.foodapps.network.ApiService
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class DetailFoodViewModel: ViewModel(), CoroutineScope {
    private lateinit var apiService: ApiService

    val onSuccess = MutableLiveData<List<FoodDetailModel>>()
    val onFailure = MutableLiveData<String>()
    val onLoading = MutableLiveData<Boolean>()


    override val coroutineContext: CoroutineContext
    get() = Job() + Dispatchers.Main

    fun setHomeService(service: ApiService) {
        this.apiService = service
    }

    fun getDetailFood(foodId: String) {
        launch {
            onLoading.value = true

            val response = withContext(Dispatchers.IO) {
                try {
                    apiService.getFoodDetail(foodId)
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

            if (response is FoodDetailResponse) {
                onLoading.value = false
                val list = response.meals.map {
                    FoodDetailModel(
                        foodId = it.foodId,
                        foodName = it.foodName,
                        foodImage = it.foodImage,
                        foodCategory = it.foodCategory,
                        foodDesc = it.foodDesc,
                        foodLocation = it.foodLocation,
                        foodTags =  it.foodTags,
                        foodVideo = it.foodVideo
                    )
                }
                onSuccess.value = list
            } else {
                onFailure.value = response.toString()
            }
        }
    }
}