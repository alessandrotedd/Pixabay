package app.alessandrotedesco.pixabay.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.alessandrotedesco.pixabay.R

@Composable
fun Label(@DrawableRes icon: Int, label: String, value: String) {
    Row(verticalAlignment = Alignment.Bottom) {
        Image(
            painter = painterResource(icon),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            contentDescription = label,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(24.dp)
        )
        Text(text = "$label ", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleMedium)
        Text(text = value, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
fun LabelPreview() {
    Label(R.drawable.heart, "Label", "Value")
}