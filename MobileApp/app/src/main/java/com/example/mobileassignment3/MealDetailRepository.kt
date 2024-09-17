package com.example.mobileassignment3

// Repository class for handling meal detail data
class MealDetailRepository {
    // Creating an instance of the Retrofit service for meal API
    private val searchService = RetrofitInstance.foodApi

    // Function to fetch a random meal response asynchronously
    suspend fun getResponse(): RandomMealResponse {
        // Calling the Retrofit service's method to get a random meal
        return searchService.getRandomMeal()
    }
}
