package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.MealItemBinding
import com.example.foodapp.pojo.MealsByCategory

class CategoryMealsAdapter() :
    RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {
    private var mealList = ArrayList<MealsByCategory>()
    var onItemClick: ((MealsByCategory)-> Unit)? = null

    fun setMealsList(mealsList: List<MealsByCategory?>?) {
        this.mealList = mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    class CategoryMealsViewHolder(var binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = mealList.size

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealList[position].strMeal

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(mealList[position])
        }
    }

}