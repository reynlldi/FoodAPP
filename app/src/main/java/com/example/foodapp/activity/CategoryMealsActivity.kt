package com.example.foodapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.adapter.CategoryMealsAdapter
import com.example.foodapp.databinding.ActivityCategoryMealsBinding
import com.example.foodapp.fragment.HomeFragment
import com.example.foodapp.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var viewModel: CategoryMealsViewModel
    private lateinit var categoryAdapter: CategoryMealsAdapter

    companion object {
        const val MEAL_ID = "com.example.foodapp.fragment.idMeal"
        const val MEAL_NAME = "com.example.foodapp.fragment.nameMeal"
        const val MEAL_THUMB = "com.example.foodapp.fragment.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()
        viewModel = ViewModelProvider(this).get(CategoryMealsViewModel::class.java)

        viewModel.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        viewModel.observerMealsLiveData().observe(this) { mealList ->
            binding.tvCategoryCount.text = "Total : ${mealList?.size.toString()}"
            categoryAdapter.setMealsList(mealList)
        }

        onCategoryClickItem()
    }

    private fun prepareRecyclerView() {
        categoryAdapter = CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

    private fun onCategoryClickItem() {
        categoryAdapter.onItemClick = { category ->
            val intent = Intent(this, MealActivity::class.java)
            intent.putExtra(MEAL_ID, category.idMeal)
            intent.putExtra(MEAL_NAME, category.strMeal)
            intent.putExtra(MEAL_THUMB, category.strMealThumb)
            startActivity(intent)
        }
    }
}