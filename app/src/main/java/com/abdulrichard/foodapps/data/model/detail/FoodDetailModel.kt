package com.abdulrichard.foodapps.data.model.detail

import com.google.gson.annotations.SerializedName

data class FoodDetailModel(
    @SerializedName("idMeal") val foodId : String,
    @SerializedName("strMeal") val foodName : String,
    @SerializedName("strCategory") val foodCategory : String,
    @SerializedName("strArea") val foodLocation : String,
    @SerializedName("strInstructions") val foodDesc : String,
    @SerializedName("strMealThumb") val foodImage : String,
    @SerializedName("strTags") val foodTags: String,
    @SerializedName("strYoutube") val foodVideo: String
)
