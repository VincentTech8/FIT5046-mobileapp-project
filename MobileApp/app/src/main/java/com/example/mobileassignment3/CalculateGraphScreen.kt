// Import necessary packages for composing UI elements and managing navigation
package com.example.mobileassignment3

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

// Suppress lint warnings for unused scaffold padding parameter and opt into experimental Material3 API
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
// Composable function to display a screen with calculated nutrient information and a button for navigation
@Composable
fun CalculateGraphScreen(
    navController: NavHostController, // Navigation controller to handle navigation
    myFood1: String?,
    myFood2: String?,
    myOunce1: Int?,
    myOunce2: Int?
) {
    // Convert nullable ounce values to non-nullable
    val myOunceFirst: Int = myOunce1!!.toInt()
    val myOunceSecond: Int = myOunce2!!.toInt()

    // List of food objects with their nutrient information
    val foodList = listOf(
        myFood("Beef", 8, 4, 0, 0),
        myFood("Chicken Breast", 9, 1, 0, 0),
        myFood("Salmon", 6, 2, 0, 0),
        myFood("White Rice", 2, 1, 16, 1),
        myFood("Avocado", 1, 5, 3, 3)
    )

    // Find the food objects corresponding to the provided food names
    val myFood1Object = foodList.find { it.foodName == myFood1 }
    val myFood2Object = foodList.find { it.foodName == myFood2 }

    // Calculate total nutrient values based on provided ounce values and food nutrient contents
    val displayProtein = myOunceFirst * myFood1Object!!.proteinContent + myOunceSecond * myFood2Object!!.proteinContent
    val displayFat = myOunceFirst * myFood1Object.fatContent + myOunceSecond * myFood2Object.fatContent
    val displayCarb = myOunceFirst * myFood1Object.carbContent + myOunceSecond * myFood2Object.carbContent
    val displayFiber = myOunceFirst * myFood1Object.fiberContent + myOunceSecond * myFood2Object.fiberContent

    // Modifier for the button
    val buttonModifier = Modifier
        .wrapContentSize()
        .fillMaxWidth(0.5f)

    // Display content in a Box aligned at the center
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {
        // Scaffold with a TopAppBar and content containing a BarChart
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Total Nutrients Today", fontWeight = FontWeight.Bold)
                    }
                )
            },
            content = {
                BarChartScreen(intValue1 = displayProtein, intValue2 = displayFat, intValue3 = displayCarb, intValue4 = displayFiber)
            }
        )

        // Text displaying nutrient names with appropriate spacing
        val spacedText = buildAnnotatedString {
            withStyle(SpanStyle(fontSize = 18.sp)) {
                append("             ")
            }
            append("Protein")
            withStyle(SpanStyle(fontSize = 18.sp)) {
                append("         ")
            }
            append("Fat")
            withStyle(SpanStyle(fontSize = 18.sp)) {
                append("          ")
            }
            append("Carb")
            withStyle(SpanStyle(fontSize = 18.sp)) {
                append("          ")
            }
            append("Fiber")
        }

        // Text displaying nutrient names with appropriate spacing and alignment
        Text(
            text = spacedText,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 250.dp),
            color = Color.Black,
            fontSize = 12.sp
        )

        // Column containing a button for navigation
        Column(
            modifier = Modifier
                .padding(top = 550.dp)
        ) {
            // Button for navigating to calculate nutrients screen
            Button(
                onClick = { navController.navigate(Routes.CalculateNutrients.value) },
                colors = ButtonDefaults.buttonColors(Color(65, 105, 225)),
                shape = RoundedCornerShape(50),
                modifier = buttonModifier
            ) {
                // Text inside the button
                Text(
                    text = "Finish Review",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(255, 255, 255),
                    fontSize = 15.sp
                )
            }
        }
    }
}
