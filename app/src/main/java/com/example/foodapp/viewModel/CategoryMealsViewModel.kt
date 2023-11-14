package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.MealsByCategory
import com.example.foodapp.pojo.MealsByCategoryList
import com.example.foodapp.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel() {
    private var mealsLiveData = MutableLiveData<List<MealsByCategory?>?>()

    fun getMealsByCategory(categoryName: String) {
        val client = ApiConfig.getApiService().getMealsByCategory(categoryName)
        client.enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                response.body().let { mealsList ->
                    mealsLiveData.postValue(mealsList?.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.e("Category Meals View Model", t.message.toString())
            }
        })
    }

    fun observerMealsLiveData(): LiveData<List<MealsByCategory?>?> {
        return mealsLiveData
    }
}