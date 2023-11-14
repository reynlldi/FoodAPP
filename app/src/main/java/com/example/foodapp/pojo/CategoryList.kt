package com.example.foodapp.pojo

data class CategoryList(
    val categories: List<CategoriesItem>
)

data class CategoriesItem(
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
)
