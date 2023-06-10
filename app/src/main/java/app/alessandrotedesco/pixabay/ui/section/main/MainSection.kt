package app.alessandrotedesco.pixabay.ui.section.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.alessandrotedesco.pixabay.apiservice.model.Image
import app.alessandrotedesco.pixabay.ui.theme.AppTheme
import coil.compose.AsyncImage

@Composable
fun MainSection(navController: NavHostController, viewModel: MainViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.searchImages("cat")
    }

    val images = viewModel.images.observeAsState().value?.hits ?: listOf()

    MainSectionUI(navController, images)
}

@Composable
fun MainSectionUI(
    navController: NavHostController,
    images: List<Image> = listOf()
) {
    val context = LocalContext.current

    Column {
        // TODO SearchBar(context)
        LazyVerticalGrid(
            columns = GridCells.Adaptive(256.dp),
            content = {
                items(images.size) {
                    ImageCard(navController, images[it])
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCard(navController: NavHostController, image: Image) {
    Card(onClick = { navController.navigate("image/${image.id}") }) {
        AsyncImage(
            model = image.previewURL,
            contentDescription = null,
        )
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