package app.alessandrotedesco.pixabay.ui.section.main

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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

    MainSectionUI(navController, images, viewModel.query, viewModel::searchImages)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainSectionUI(
    navController: NavHostController,
    images: List<Image> = listOf(),
    query: MutableState<String> = mutableStateOf(""),
    searchImages: (String) -> Unit = {},
) {
    Column {
        SearchBar(
            onSearch = { searchImages(it) },
            query = query.value,
            onQueryChange = { query.value = it },
            active = true,
            content = {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    content = {
                        items(images.size) {
                            ImageCard(images[it]) {
                                Toast.makeText(
                                    navController.context,
                                    images[it].tags,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    userScrollEnabled = true,
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.Center
                )
            },
            onActiveChange = { },
        )
    }
}

@Composable
fun ImageCard(image: Image, onClick: () -> Unit = {}) {
    AsyncImage(
        model = image.webformatURL,
        contentDescription = image.tags,
        modifier = Modifier.clickable { onClick() }.size(128.dp),
        contentScale = ContentScale.Crop
    )
}

@Preview(showBackground = true)
@Composable
fun MainSectionPreview() {
    AppTheme {
        val navController = rememberNavController()
        MainSectionUI(navController)
    }
}