package com.abdulrichard.foodapps.ui.home.module

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abdulrichard.foodapps.R
import com.abdulrichard.foodapps.data.model.home.FoodModel
import com.abdulrichard.foodapps.databinding.ItemFoodListBinding
import com.abdulrichard.foodapps.ui.detail.DetailFoodActivity
import com.squareup.picasso.Picasso

class HomeAdapter (private val context: Context): RecyclerView.Adapter<HomeAdapter.FoodListViewHolder>() {
    private var items = mutableListOf<FoodModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: List<FoodModel>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FoodListViewHolder(inflater, parent)
    }

    inner class FoodListViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(
        R.layout.item_food_list, parent, false)) {
        private val binding = ItemFoodListBinding.bind(itemView)

        fun bind(data: FoodModel) {
            with(binding){

                Picasso.get()
                    .load(data.foodImage)
                    .resize(582, 512)
                    .centerCrop()
                    .into(ivFood)

                tvFoodName.text = data.foodName
            }

        }
    }

    override fun onBindViewHolder(holder: FoodListViewHolder, position: Int) {
        val food = items[position]
        holder.bind(food)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailFoodActivity::class.java)
            intent.putExtra("foodId", food.foodId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size

}