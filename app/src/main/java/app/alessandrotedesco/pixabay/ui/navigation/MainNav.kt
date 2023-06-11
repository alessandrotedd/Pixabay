package app.alessandrotedesco.pixabay.ui.navigation

sealed class MainNav(val route: String) {
    object Search : MainNav("search")
    object Detail : MainNav("detail")
}