package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.local.MealDatabase
import com.example.foodapp.pojo.CategoriesItem
import com.example.foodapp.pojo.CategoryList
import com.example.foodapp.pojo.MealList
import com.example.foodapp.pojo.MealsByCategory
import com.example.foodapp.pojo.MealsByCategoryList
import com.example.foodapp.pojo.MealsItem
import com.example.foodapp.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealDatabase: MealDatabase) : ViewModel() {
    private var randomMealLiveData = MutableLiveData<MealsItem?>()
    private var popularItemLiveData = MutableLiveData<List<MealsByCategory?>?>()
    private var categoriesLiveData = MutableLiveData<List<CategoriesItem?>?>()
    private var favoritesMealLiveData = mealDatabase.mealDao().getAllMeals()

    fun getRandomMeal() {
        val client = ApiConfig.getApiService().getRandomMeal()
        client.enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: MealsItem? = response.body()?.meals?.get(0)
                    randomMealLiveData.value = randomMeal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home Fragment", t.message.toString())
            }
        })
    }

    fun getPopularItem() {
        val client = ApiConfig.getApiService().getPopularItem("Seafood")
        client.enqueue(object: Callback<MealsByCategoryList>{
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                if (response.body() != null){
                    popularItemLiveData.value = response.body()?.meals
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("Home Fragment", t.message.toString())
            }
        })
    }

    fun getCategories(){
        val client = ApiConfig.getApiService().getCategories()
        client.enqueue(object: Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body().let {categoryList ->
                    categoriesLiveData.postValue(categoryList?.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("Home Fragment", t.message.toString())
            }
        })
    }

    fun observeRandomMealLiveData(): LiveData<MealsItem?> {
        return randomMealLiveData
    }

    fun observePopularLiveData(): LiveData<List<MealsByCategory?>?> {
        return popularItemLiveData
    }

    fun observeCategoriesLiveData(): LiveData<List<CategoriesItem?>?> {
        return categoriesLiveData
    }

    fun observeFavoriteMealsLiveData(): LiveData<List<MealsItem>>{
        return favoritesMealLiveData
    }

    fun deleteMeal(meal: MealsItem){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun insertMeal(meal: MealsItem){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }
}