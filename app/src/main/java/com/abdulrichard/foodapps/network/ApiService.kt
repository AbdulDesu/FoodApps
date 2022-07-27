package com.abdulrichard.foodapps.network

import com.abdulrichard.foodapps.data.response.detail.FoodDetailResponse
import com.abdulrichard.foodapps.data.response.home.HomeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("filter.php?c=Seafood")
    suspend fun getFoodList() : HomeResponse

    @GET("lookup.php")
    suspend fun getFoodDetail(@Query("i") foodId: String) : FoodDetailResponse
}