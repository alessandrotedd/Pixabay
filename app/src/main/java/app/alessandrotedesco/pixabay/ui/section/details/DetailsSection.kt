package app.alessandrotedesco.pixabay.ui.section.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.alessandrotedesco.pixabay.apiservice.model.Image
import app.alessandrotedesco.pixabay.ui.Label
import app.alessandrotedesco.pixabay.ui.TagsRow
import app.alessandrotedesco.pixabay.ui.theme.AppTheme
import coil.ImageLoader
import coil.compose.AsyncImage
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

    Column(Modifier.verticalScroll(rememberScrollState())) {
        AsyncImage(
            model = image.largeImageURL,
            contentDescription = image.tags,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth()
        )
        Label("User", image.user)
        Label("Likes", image.likes.toString())
        Label("Downloads", image.downloads.toString())
        Label("Comments", image.comments.toString())
        TagsRow(image.tags.split(","))
    }
}



@Preview(showBackground = true)
@Composable
fun MainSectionPreview() {
    AppTheme {
        DetailsSectionUI(Image())
    }
}