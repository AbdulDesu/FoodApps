package com.abdulrichard.foodapps.data.model.home

import com.google.gson.annotations.SerializedName

data class FoodModel(
    @SerializedName("strMeal") val foodName : String,
    @SerializedName("strMealThumb") val foodImage : String,
    @SerializedName("idMeal") val foodId : String,
)
