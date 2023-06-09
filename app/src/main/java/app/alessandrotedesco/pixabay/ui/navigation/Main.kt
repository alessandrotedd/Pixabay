package app.alessandrotedesco.pixabay.ui.navigation

sealed class Main(val route: String) {
    object Example : Main("example")
}