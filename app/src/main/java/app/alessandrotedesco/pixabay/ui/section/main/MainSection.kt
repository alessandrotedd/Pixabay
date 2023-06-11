package app.alessandrotedesco.pixabay.ui.section.main

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.alessandrotedesco.pixabay.apiservice.model.Image
import app.alessandrotedesco.pixabay.ui.ImageCard
import app.alessandrotedesco.pixabay.ui.theme.AppTheme
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache

@Composable
fun MainSection(navController: NavHostController, viewModel: MainViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.searchImages(viewModel.query.value)
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
    searchImages: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizePercent(0.02)
                .build()
        }
        .build()

    Column {
        SearchBar(
            onSearch = { searchImages(it) },
            query = query.value,
            onQueryChange = { query.value = it },
            active = true,
            onActiveChange = { }
        ) {
            LazyColumn(
                Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                userScrollEnabled = true,
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(images.size) {
                    ImageCard(images[it], imageLoader) {
                        Toast.makeText(
                            navController.context,
                            images[it].tags,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
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