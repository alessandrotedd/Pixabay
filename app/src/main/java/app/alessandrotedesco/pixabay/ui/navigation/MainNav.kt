package app.alessandrotedesco.pixabay.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class MainNav(val route: String, val arguments: List<NamedNavArgument> = emptyList()) {
    object Search : MainNav("search")
    object Detail : MainNav(
        "detail/{imageId}",
        listOf(navArgument("imageId") { type = NavType.StringType })
    )
}