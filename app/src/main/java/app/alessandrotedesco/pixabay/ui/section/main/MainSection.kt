package app.alessandrotedesco.pixabay.ui.section.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.alessandrotedesco.pixabay.apiservice.model.Image
import app.alessandrotedesco.pixabay.ui.theme.AppTheme
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.ImageRequest

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

@Composable
fun ImageCard(image: Image, imageLoader: ImageLoader, onClick: () -> Unit = {}) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(data = image.webformatURL)
            .crossfade(true)
            .build(),
        imageLoader = imageLoader
    )

    Card(Modifier.clickable { onClick.invoke() }) {
        Row(Modifier.fillMaxWidth()) {
            androidx.compose.foundation.Image(
                painter = painter,
                contentDescription = image.tags,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(128.dp)
            )
            Column(verticalArrangement = Arrangement.SpaceAround) {
                Text(
                    text = image.user,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                Row(
                    Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    image.tags.split(",").forEach {
                        Box(Modifier
                            .shadow(4.dp)
                            .clickable { }
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary)
                        ) {
                            Text(
                                text = it,
                                Modifier.padding(8.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
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