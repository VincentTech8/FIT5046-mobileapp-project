package com.example.mobileassignment3

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton object responsible for providing Retrofit instances
object RetrofitInstance {

    // Lazily initializes a Retrofit instance for accessing the FoodApi
    val foodApi:FoodApi by lazy {
        Log.i("RetrofitObject", "called")
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FoodApi::class.java)
    }
}
