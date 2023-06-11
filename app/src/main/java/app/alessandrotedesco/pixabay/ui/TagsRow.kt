package app.alessandrotedesco.pixabay.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TagsRow(tags: List<String>) {
    if (tags.isEmpty()) return

    Row(
        Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.forEach {
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

@Preview
@Composable
fun TagsRowPreview() {
    TagsRow(listOf("tag1", "tag2", "tag3", "tag4", "tag5", "tag6"))
}