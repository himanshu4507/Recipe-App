package com.hashdroid.recipeapp.network

data class RecipeResponse(
    val recipes: List<Recipe>
)

data class Recipe(
    val title: String,
    val image: String
)
