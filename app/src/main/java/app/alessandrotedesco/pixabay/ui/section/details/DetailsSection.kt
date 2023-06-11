package app.alessandrotedesco.pixabay.ui.section.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import app.alessandrotedesco.pixabay.R
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
    val tags = image.tags.split(",").let {
        if (it.size == 1 && it.first() == "") emptyList() else it
    }

    Column(Modifier.verticalScroll(rememberScrollState())) {
        AsyncImage(
            model = image.largeImageURL,
            contentDescription = image.tags,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth(),
            placeholder = painterResource(id = R.drawable.picture)
        )
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Label(R.drawable.user,"User", image.user)
            Label(R.drawable.heart, "Likes", image.likes.toString())
            Label(R.drawable.download, "Downloads", image.downloads.toString())
            Label(R.drawable.comment, "Comments", image.comments.toString())
        }
        TagsRow(tags)
    }
}



@Preview(showBackground = true)
@Composable
fun MainSectionPreview() {
    AppTheme {
        DetailsSectionUI(Image())
    }
}