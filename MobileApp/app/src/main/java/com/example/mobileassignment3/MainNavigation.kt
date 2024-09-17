package com.example.mobileassignment3

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// Function to create the main navigation layout
@Composable
fun MainNavigation(
    viewModel: FoodViewModel,
    viewModel1: SubjectViewModel,
    onLogout: () -> Unit // Add a callback for logout action
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState() // Remembering the navigation controller
            val currentDestination = navBackStackEntry?.destination
            // Checking if the current destination is not the review or graph screens
            if (currentDestination?.route !in listOf(
                    Routes.CalculateReviewScreen.value,
                    Routes.CalculateGraphScreen.value
                )
            ) {
                // Creating bottom navigation bar
                BottomNavigation(backgroundColor = Color.LightGray) {
                    // Iterating through each navigation item
                    NavBarItem().NavBarItems().forEach { navItem ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    navItem.icon, contentDescription = null
                                )
                            },
                            label = { Text(navItem.label) },
                            selected = currentDestination?.hierarchy?.any {
                                it.route == navItem.route
                            } == true,
                            onClick = {
                                // Navigating to the selected destination
                                navController.navigate(navItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = false
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        // Setting up navigation host
        NavHost(
            navController,
            startDestination = Routes.Home.value,
            Modifier.padding(paddingValues)
        ) {
            // Defining composable destinations
            composable(Routes.Home.value) {
                Home(navController, viewModel)
            }
            composable(Routes.RecipeNotes.value) {
                RecipeNotes(navController, viewModel1)
            }
            composable(Routes.CalculateNutrients.value) {
                CalculateNutrients(navController)
            }
            // Defining composable destination for review screen with arguments
            composable(
                route = Routes.CalculateReviewScreen.value + "?food1={food1}&food2={food2}&ounce1={ounce1}&ounce2={ounce2}",
                arguments = listOf(
                    navArgument(name = "food1") {
                        type = NavType.StringType
                        defaultValue = "Beef"
                    },
                    navArgument(name = "food2") {
                        type = NavType.StringType
                        defaultValue = "Chicken Breast"
                    },
                    navArgument(name = "ounce1") {
                        type = NavType.IntType
                        defaultValue = 1
                    },
                    navArgument(name = "ounce2") {
                        type = NavType.IntType
                        defaultValue = 1
                    }
                )
            ) { backstackEntry ->
                CalculateReviewScreen(
                    navController,
                    myFood1 = backstackEntry.arguments?.getString("food1"),
                    myFood2 = backstackEntry.arguments?.getString("food2"),
                    myOunce1 = backstackEntry.arguments?.getInt("ounce1"),
                    myOunce2 = backstackEntry.arguments?.getInt("ounce2")
                )
            }
            // Defining composable destination for graph screen with arguments
            composable(
                route = Routes.CalculateGraphScreen.value + "?food1={food1}&food2={food2}&ounce1={ounce1}&ounce2={ounce2}",
                arguments = listOf(
                    navArgument(name = "food1") {
                        type = NavType.StringType
                        defaultValue = "Beef"
                    },
                    navArgument(name = "food2") {
                        type = NavType.StringType
                        defaultValue = "Chicken Breast"
                    },
                    navArgument(name = "ounce1") {
                        type = NavType.IntType
                        defaultValue = 1
                    },
                    navArgument(name = "ounce2") {
                        type = NavType.IntType
                        defaultValue = 1
                    }
                )
            ) { backstackEntry ->
                CalculateGraphScreen(
                    navController,
                    myFood1 = backstackEntry.arguments?.getString("food1"),
                    myFood2 = backstackEntry.arguments?.getString("food2"),
                    myOunce1 = backstackEntry.arguments?.getInt("ounce1"),
                    myOunce2 = backstackEntry.arguments?.getInt("ounce2")
                )
            }
        }
        // Button for logout action
        Button(
            onClick = onLogout, // Call the logout callback when clicked
            modifier = Modifier
                .padding(start = 290.dp, top = 670.dp),
            colors = ButtonDefaults.buttonColors(Color(65, 105, 225))
        ) {
            Text("Log out")
        }
    }
}
