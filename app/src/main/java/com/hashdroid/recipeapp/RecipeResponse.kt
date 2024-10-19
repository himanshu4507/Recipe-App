package com.hashdroid.recipeapp

data class RecipeResponse(
    val recipes: List<Recipe>
)

data class Recipe(
    val title: String,
    val image: String
)
