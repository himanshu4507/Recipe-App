package com.hashdroid.recipeapp

data class RecipeResponse(
    val recipes: List<Recipe>
)
data class RecipeResponse2(
    val results: List<Recipe>
)


data class Recipe(
    val title: String,
    val image: String,
    val id:Int
)
