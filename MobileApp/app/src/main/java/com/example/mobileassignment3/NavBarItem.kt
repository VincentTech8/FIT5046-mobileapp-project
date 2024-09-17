package com.example.mobileassignment3

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

// Data class representing a navigation item
data class NavBarItem(
    val label: String = "",
    val icon: ImageVector = Icons.Filled.Home,
    val route: String = ""
) {
    // Function to generate a list of navigation items
    fun NavBarItems(): List<NavBarItem> {
        return listOf(
            NavBarItem(
                label = "Home",
                icon = Icons.Outlined.Home,
                route = Routes.Home.value
            ),
            NavBarItem(
                label = "My Recipe",
                icon = Icons.Filled.FavoriteBorder,
                route = Routes.RecipeNotes.value
            ),
            NavBarItem(
                label = "Nutrient",
                icon = Icons.Outlined.Analytics,
                route = Routes.CalculateNutrients.value
            ),
        )
    }
}
