// Import necessary packages for composing UI elements, navigation, ViewModel, and image loading
package com.example.mobileassignment3

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter

// Composable function for the home screen
@Composable
fun Home(navController: NavHostController, viewModel: FoodViewModel) {

    // Initialize variables for meal details
    var mealName=""
    var mealCategory=""
    var mealYoutube=""
    var url=""

    // Retrieve data from ViewModel
    val itemsReturned by viewModel.retrofitResponse
    val list = itemsReturned.meals
    if(list.isNotEmpty()) {
        val result1: String = list[0].strMeal
        mealName = result1
        val result2: String = list[0].strCategory
        mealCategory = result2
        val result4: String = list[0].strYoutube
        mealYoutube = result4
        val result5: String = list[0].strMealThumb
        url = result5
    }

    // Container to hold UI elements
    Box(
        modifier = Modifier.fillMaxSize()
    )
    {
        Column(
            modifier = Modifier.padding(10.dp, top = 30.dp, end = 10.dp)
        ) {
            // Text welcoming the user
            Text(
                text = "Hi there \uD83D\uDE0A",
                fontSize = 20.sp,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            )

            // Text encouraging user to explore ingredients and cook together
            Text(
                text = "Let's understand our ingredients and cook together !",
                fontSize = 20.sp,
                color = Color.Black.copy(alpha = 0.7f),
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier
                    .padding(start = 10.dp, top = 20.dp, bottom = 20.dp)
            )

            // Text prompting user to choose a food
            Text(
                text = "What would you like to eat?",
                fontSize = 20.sp,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )

            // Button for suggesting another food
            Button(
                onClick = {
                    viewModel.getResponse() // Call ViewModel function to get a response
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(Color(65, 105, 225))
            ) {
                Text("Suggest me another food")
            }

            // Display meal image
            val displayMealUrl = url.ifEmpty {
                "https://www.themealdb.com/images/media/meals/xxyupu1468262513.jpg"
            }

            // Column to hold UI elements related to meal details
            Column(
                modifier = Modifier.padding(top = 10.dp)
            )
            {
                // Image displaying the meal
                Image(
                    painter = rememberAsyncImagePainter(model = displayMealUrl),
                    contentDescription = "Picture of food",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(2.dp, Color.Black, shape = RoundedCornerShape(16.dp))
                )
            }

            // Text displaying food name
            Text(
                text = "Food Name:",
                fontSize = 18.sp,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 0.dp)
            )

            // Display meal name
            val displayMealName = mealName.ifEmpty {
                "Honey Teriyaki Salmon"
            }

            // Text displaying the meal name
            Text(
                text = displayMealName,
                fontSize = 18.sp,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
            )

            // Text displaying food category
            Text(
                text = "Category:",
                fontSize = 18.sp,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 0.dp)
            )

            // Display meal category
            val displayMealCategory = mealCategory.ifEmpty {
                "Seafood"
            }

            // Text displaying the meal category
            Text(
                text = displayMealCategory,
                fontSize = 18.sp,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
            )

            // Text displaying YouTube link
            Text(
                text = "Youtube:",
                fontSize = 18.sp,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 0.dp)
            )

            // Display YouTube link
            val displayMealYoutube = mealYoutube.ifEmpty {
                "https://www.youtube.com/watch?v=4MpYuaJsvRw"
            }

            // Text displaying the YouTube link
            Text(
                text = displayMealYoutube,
                fontSize = 18.sp,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
            )
        }
    }
}
