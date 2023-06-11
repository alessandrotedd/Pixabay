package app.alessandrotedesco.pixabay.ui.section.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.alessandrotedesco.pixabay.apiservice.model.Image
import app.alessandrotedesco.pixabay.ui.theme.AppTheme
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import timber.log.Timber

@Composable
fun DetailsSection(viewModel: DetailsViewModel = hiltViewModel()) {
    val image = viewModel.image.collectAsState().value
    DetailsSectionUI(image)
}

@Composable
fun DetailsSectionUI(image: Image) {
    Timber.i("DetailsSectionUI: $image")
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
            .data(image.largeImageURL)
            .crossfade(true)
            .networkCachePolicy(CachePolicy.READ_ONLY)
            .build(),
        imageLoader = imageLoader
    )

    Image(
        painter = painter,
        contentDescription = image.tags,
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(128.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun MainSectionPreview() {
    AppTheme {
        DetailsSectionUI(Image())
    }
}