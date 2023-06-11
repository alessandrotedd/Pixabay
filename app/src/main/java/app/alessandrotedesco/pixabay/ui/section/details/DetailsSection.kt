package app.alessandrotedesco.pixabay.ui.section.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
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
import coil.request.CachePolicy
import coil.request.ImageRequest

@Composable
fun DetailsSection(imageId: String, viewModel: DetailsViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.searchImages(viewModel.query.value)
    }

    DetailsSectionUI(imageId)
}

@Composable
fun DetailsSectionUI(imageId: String) {

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

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(data = imageId)
            .crossfade(true)
            .networkCachePolicy(CachePolicy.READ_ONLY)
            .build(),
        imageLoader = imageLoader
    )

    Image(
        painter = painter,
        contentDescription = imageId,
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(128.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun MainSectionPreview() {
    AppTheme {
        DetailsSectionUI("123")
    }
}