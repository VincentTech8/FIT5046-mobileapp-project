package com.example.mobileassignment3

import retrofit2.http.GET

// Define an interface named FoodApi
interface FoodApi {

    // Define a suspend function named getRandomMeal() with a GET annotation
    @GET("random.php")
    suspend fun getRandomMeal(): RandomMealResponse
}