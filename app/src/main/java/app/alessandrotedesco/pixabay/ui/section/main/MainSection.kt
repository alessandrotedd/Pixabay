package app.alessandrotedesco.pixabay.ui.section.main

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.alessandrotedesco.pixabay.R
import app.alessandrotedesco.pixabay.apiservice.model.Image
import app.alessandrotedesco.pixabay.ui.ImageCard
import app.alessandrotedesco.pixabay.ui.navigation.MainNav
import app.alessandrotedesco.pixabay.ui.theme.AppTheme
import app.alessandrotedesco.pixabay.utils.getImageLoader

@Composable
fun MainSection(navController: NavHostController, viewModel: MainViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val images = viewModel.images.observeAsState().value
    var onError by viewModel.onError

    LaunchedEffect(Unit) {
        viewModel.searchImages(viewModel.query.value)
    }
    LaunchedEffect(onError) {
        if (onError) {
            Toast.makeText(context, "Can't load images, check your internet connection and try again", Toast.LENGTH_LONG).show()
            onError = false
        }
    }

    MainSectionUI(
        navController,
        images,
        viewModel.query,
        viewModel::searchImages,
        viewModel::cacheImage
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainSectionUI(
    navController: NavHostController,
    images: List<Image>? = listOf(),
    query: MutableState<String> = mutableStateOf(""),
    searchImages: (String) -> Unit = {},
    onImageSelected: (Image) -> Unit = {},
) {
    var selectedImage: Image? by remember { mutableStateOf(null) }
    val showDialog = selectedImage != null
    val context = LocalContext.current
    val imageLoader = getImageLoader(context)

    LaunchedEffect(selectedImage) {
        selectedImage?.let { image -> onImageSelected.invoke(image) }
    }

    Column {
        SearchBar(
            onSearch = { searchImages(it) },
            query = query.value,
            onQueryChange = { query.value = it },
            active = true,
            onActiveChange = { }
        ) {
            images?.let {
                if (images.isEmpty()) {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Oops! No images found", Modifier.padding(16.dp))
                        androidx.compose.foundation.Image(
                            painter = painterResource(id = R.drawable.no_results),
                            modifier = Modifier.size(300.dp),
                            contentScale = ContentScale.Fit,
                            contentDescription = null
                        )
                    }
                }

                LazyColumn(
                    Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    userScrollEnabled = true,
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(images.size) {
                        ImageCard(images[it], imageLoader) {
                            selectedImage = images[it]
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { selectedImage = null },
            title = { Text("See more details?") },
            text = { Text("Do you want to see more details for this image?") },
            confirmButton = {
                TextButton(onClick = {
                    navController.navigate(MainNav.Detail.route)
                    selectedImage = null
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedImage = null }) {
                    Text("No")
                }
            }
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