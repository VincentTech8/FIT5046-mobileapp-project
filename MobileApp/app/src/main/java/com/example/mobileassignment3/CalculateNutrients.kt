// Import necessary packages for composing UI elements and managing navigation
package com.example.mobileassignment3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType

// Opt in to use ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class)
// Composable function to display a screen for calculating nutrient intake
@Composable
fun CalculateNutrients(navController: NavHostController) {
    // Modifier for the button
    val buttonModifier = Modifier
        .wrapContentSize()
        .fillMaxWidth(0.5f)

    // Initialize mutable state variables for selected food options and ounce values
    var foodOunce1 by remember { mutableStateOf("1") }
    var foodOunce2 by remember { mutableStateOf("1") }

    // Lists of food options for dropdown menus
    val foods1 = listOf(
        "Beef",
        "Chicken Breast",
        "Salmon",
        "White Rice",
        "Avocado"
    )
    val foods2 = listOf(
        "Chicken Breast",
        "Beef",
        "Salmon",
        "White Rice",
        "Avocado"
    )

    // Initialize mutable state variables for managing dropdown menu expansion and selected food options
    var isExpanded1 by remember { mutableStateOf(false) }
    var isExpanded2 by remember { mutableStateOf(false) }
    var selectedFood1 by remember { mutableStateOf(foods1[0]) }
    var selectedFood2 by remember { mutableStateOf(foods2[0]) }

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
                text = "Select two foods you ate today:",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                ),
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.6f)
                        .padding(8.dp)
                        .height(80.dp)
                ) {
                    // Content of the first split
                    ExposedDropdownMenuBox(
                        expanded = isExpanded1,
                        onExpandedChange = { isExpanded1 = it },
                    ) {
                        TextField(
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .focusProperties {
                                    canFocus = false
                                }
                                .padding(bottom = 8.dp),
                            readOnly = true,
                            value = selectedFood1,
                            onValueChange = {},
                            label = { Text("Food") },
                            //manages the arrow icon up and down
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded1)
                            },
                        )
                        ExposedDropdownMenu(
                            expanded = isExpanded1,
                            onDismissRequest = { isExpanded1 = false }
                        )
                        {
                            foods1.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption) },
                                    onClick = {
                                        selectedFood1 = selectionOption
                                        isExpanded1 = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                )
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(0.25f)
                        .height(80.dp)
                ) {
                    // Content of the second split
                    OutlinedTextField(
                        value = foodOunce1,
                        onValueChange = { foodOunce1 = it },
                        label = { Text("Ounce") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.6f)
                        .padding(8.dp)
                        .height(80.dp)
                ) {
                    // Content of the first split
                    ExposedDropdownMenuBox(
                        expanded = isExpanded2,
                        onExpandedChange = { isExpanded2 = it },
                    ) {
                        TextField(
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .focusProperties {
                                    canFocus = false
                                }
                                .padding(bottom = 8.dp),
                            readOnly = true,
                            value = selectedFood2,
                            onValueChange = {},
                            label = { Text("Food") },
                            //manages the arrow icon up and down
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded2)
                            },
                        )
                        ExposedDropdownMenu(
                            expanded = isExpanded2,
                            onDismissRequest = { isExpanded2 = false }
                        )
                        {
                            foods2.forEach { selectionOption ->
                                DropdownMenuItem(
                                    text = { Text(selectionOption) },
                                    onClick = {
                                        selectedFood2 = selectionOption
                                        isExpanded2 = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                )
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(0.25f)
                        .height(80.dp)
                ) {
                    // Content of the second split
                    OutlinedTextField(
                        value = foodOunce2,
                        onValueChange = { foodOunce2 = it },
                        label = { Text("Ounce") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 10.dp)
                    )
                }
            }

            Button(
                onClick = {
                    // Route with parameters
                    val myRoute = Routes.CalculateReviewScreen.value + "?food1=$selectedFood1&food2=$selectedFood2&ounce1=$foodOunce1&ounce2=$foodOunce2"
                    // Navigate to the review screen with calculated nutrients
                    navController.navigate(myRoute)
                },
                colors = ButtonDefaults.buttonColors(Color(65, 105, 225)),
                shape = RoundedCornerShape(50),
                modifier = buttonModifier
            ) {
                Text(
                    text = "Calculate",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(255, 255, 255),
                    fontSize = 15.sp
                )
            }
        }
    }
}
