package com.example.foodapp.pojo

import com.google.gson.annotations.SerializedName

data class MealsByCategoryList(
    val meals: List<MealsByCategory>
)

data class MealsByCategory(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)
