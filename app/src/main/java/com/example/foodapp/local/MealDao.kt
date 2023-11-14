package com.example.foodapp.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodapp.pojo.MealsItem

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: MealsItem)

    @Delete
    suspend fun delete(meal: MealsItem)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeals():LiveData<List<MealsItem>>
}