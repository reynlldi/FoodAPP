package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.local.MealDatabase
import com.example.foodapp.pojo.MealList
import com.example.foodapp.pojo.MealsItem
import com.example.foodapp.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(val mealDatabase: MealDatabase) : ViewModel() {
    private var mealDetailLiveData = MutableLiveData<MealsItem>()

    fun getMealDetail(id: String) {
        val client = ApiConfig.getApiService().getMealDetails(id)
        client.enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    mealDetailLiveData.value = response.body()!!.meals?.get(0)
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MainActivity", t.message.toString())
            }
        })
    }

    fun observerMealDetailLiveData():LiveData<MealsItem>{
        return mealDetailLiveData
    }

    fun insertMeal(meal: MealsItem){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }
}