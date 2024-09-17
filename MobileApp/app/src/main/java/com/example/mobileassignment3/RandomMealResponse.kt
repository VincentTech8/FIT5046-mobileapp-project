package com.example.mobileassignment3

// Data class representing a response containing random meal details
data class RandomMealResponse(
    val meals: List<MealDetail> = ArrayList() // List of meal details, initialized as an empty ArrayList
)