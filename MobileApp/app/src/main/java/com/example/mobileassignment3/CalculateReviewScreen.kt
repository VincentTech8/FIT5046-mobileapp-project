// Import necessary packages for composing UI elements and managing navigation
package com.example.mobileassignment3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

// Composable function for displaying the review screen with calculated nutrient details
@Composable
fun CalculateReviewScreen(
    navController: NavHostController, // Navigation controller for navigating to other screens
    myFood1: String?,
    myFood2: String?,
    myOunce1: Int?,
    myOunce2: Int?
) {
    // Modifier for the button
    val buttonModifier = Modifier
        .wrapContentSize()
        .fillMaxWidth(0.5f)

    // Display content in a Box aligned at the top center
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    )
    {
        Column(
            modifier = Modifier.padding(top = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Details Screen",
                fontSize = 30.sp,
                style = TextStyle(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Column {
                Text(text = "First Food: $myFood1", fontSize = 20.sp)
                Text(text = "Total Ounce: $myOunce1", fontSize = 20.sp)
                Text(text = "Second Food: $myFood2", fontSize = 20.sp)
                Text(text = "Total Ounce: $myOunce2", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    // Construct route with parameters for the graph screen
                    val myRoute =
                        Routes.CalculateGraphScreen.value + "?food1=$myFood1&food2=$myFood2&ounce1=$myOunce1&ounce2=$myOunce2"
                    navController.navigate(myRoute) // Navigate to the graph screen
                },
                colors = ButtonDefaults.buttonColors(Color(65, 105, 225)),
                shape = RoundedCornerShape(50),
                modifier = buttonModifier
            ) {
                Text(
                    text = "Proceed",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(255, 255, 255),
                    fontSize = 15.sp
                )
            }
        }
    }
}
