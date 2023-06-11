package app.alessandrotedesco.pixabay.ui.section.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.alessandrotedesco.pixabay.R
import app.alessandrotedesco.pixabay.apiservice.model.Image
import app.alessandrotedesco.pixabay.ui.Label
import app.alessandrotedesco.pixabay.ui.TagsRow
import app.alessandrotedesco.pixabay.ui.theme.AppTheme
import coil.compose.AsyncImage

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
    val aspectRatio = image.imageWidth / image.imageHeight.toFloat()

    var imageLoaded by remember { mutableStateOf(false) }
    val colorFilter = if (imageLoaded) null else ColorFilter.tint(MaterialTheme.colorScheme.onSurface)

    Column(Modifier.verticalScroll(rememberScrollState())) {
        AsyncImage(
            model = image.largeImageURL,
            contentDescription = image.tags,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth().aspectRatio(aspectRatio),
            placeholder = painterResource(R.drawable.picture),
            onSuccess = { imageLoaded = true },
            colorFilter = colorFilter
        )

        Column(Modifier.padding(16.dp).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Label(R.drawable.user, "User", image.user)
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