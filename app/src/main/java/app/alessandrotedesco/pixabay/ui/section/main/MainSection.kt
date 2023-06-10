package app.alessandrotedesco.pixabay.ui.section.main

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.alessandrotedesco.pixabay.ui.theme.AppTheme
import app.alessandrotedesco.pixabay.utils.createNotificationChannel

@Composable
fun MainSection(navController: NavHostController, viewModel: MainViewModel = hiltViewModel()) {
    MainSectionUI(navController)
}

@Composable
fun MainSectionUI(
    navController: NavHostController
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        context.createNotificationChannel("channelId", "channelName", "channelDescription")
    }

    Column {

    }
}

@Preview(showBackground = true)
@Composable
fun MainSectionPreview() {
    AppTheme {
        val navController = rememberNavController()
        MainSectionUI(navController)
    }
}