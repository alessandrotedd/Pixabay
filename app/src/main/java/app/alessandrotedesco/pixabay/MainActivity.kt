package app.alessandrotedesco.pixabay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.alessandrotedesco.pixabay.ui.navigation.MainNav
import app.alessandrotedesco.pixabay.ui.section.details.DetailsSection
import app.alessandrotedesco.pixabay.ui.section.main.MainSection
import app.alessandrotedesco.pixabay.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController, startDestination = MainNav.Search.route) {
                        composable(MainNav.Search.route) {
                            MainSection(navController)
                        }
                        composable(MainNav.Detail.route) {
                            DetailsSection()
                        }
                    }
                }
            }
        }
    }
}