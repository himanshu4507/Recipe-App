package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FavouritesAdapter(
    private var favouritesList: List<FavouritesEntity>, // Data list
    private val onItemClick: (FavouritesEntity) -> Unit // Lambda for item click
) : RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>() {

    // ViewHolder class
    class FavouritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dishImage: ImageView = view.findViewById(R.id.dish_image2)
        val dishName: TextView = view.findViewById(R.id.dish_name2)
        val cookTime: TextView = view.findViewById(R.id.cook_time2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_view2, parent, false)
        return FavouritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val favourite = favouritesList[position]

        // Bind data to views
        Glide.with(holder.itemView.context).load(favourite.image).into(holder.dishImage)
        holder.dishName.text = favourite.title
        holder.cookTime.text = "${favourite.cookingTime} mins"

        // Handle click listener
        holder.itemView.setOnClickListener {
            onItemClick(favourite)
        }
    }

    override fun getItemCount(): Int = favouritesList.size

    // Method to update the data and notify adapter
    fun updateData(newList: List<FavouritesEntity>) {
        favouritesList = newList
        notifyDataSetChanged()
    }
}
