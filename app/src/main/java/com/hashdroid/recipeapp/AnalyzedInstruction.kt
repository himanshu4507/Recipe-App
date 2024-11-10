package com.hashdroid.recipeapp

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)