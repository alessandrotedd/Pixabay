package app.alessandrotedesco.pixabay.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.alessandrotedesco.pixabay.apiservice.model.Image
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest

@Composable
fun ImageCard(image: Image, imageLoader: ImageLoader, onClick: () -> Unit = {}) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(image.webformatURL)
            .crossfade(true)
            .networkCachePolicy(CachePolicy.READ_ONLY)
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

@Preview
@Composable
fun ImageCardPreview() {
    ImageCard(
        image = Image(
            user = "user",
            tags = "tags",
            webformatURL = "https://via.placeholder.com/150"
        ),
        imageLoader = ImageLoader.Builder(LocalContext.current).build()
    )
}