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
    searchImages: (String) -> Unit = {},
) {
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
                    ImageCard(images[it]) {
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
fun ImageCard(image: Image, onClick: () -> Unit = {}) {
    Card(Modifier.clickable { onClick }) {
        Row(Modifier.fillMaxWidth()) {
            AsyncImage(
                model = image.webformatURL,
                contentDescription = image.tags,
                modifier = Modifier.size(128.dp),
                contentScale = ContentScale.Crop
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