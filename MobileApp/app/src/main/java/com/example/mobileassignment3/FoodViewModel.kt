// Import necessary packages for logging, managing state, ViewModel, coroutines, and repository
package com.example.mobileassignment3

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// Define a ViewModel class named FoodViewModel
class FoodViewModel: ViewModel() {
    // Create an instance of MealDetailRepository
    private val repository = MealDetailRepository()

    // MutableState for holding Retrofit response with default value
    val retrofitResponse: MutableState<RandomMealResponse> = mutableStateOf(RandomMealResponse())

    // Function to get response from repository
    fun getResponse() {
        // Launch a coroutine within ViewModel scope
        viewModelScope.launch {
            try {
                // Call repository function to get response
                val responseReturned = repository.getResponse()
                // Update the value of retrofitResponse MutableState
                retrofitResponse.value = responseReturned
            } catch (e: Exception) {
                // Catch any exceptions and log error message
                Log.i("Error ", "Response failed")
            }
        }
    }
}
