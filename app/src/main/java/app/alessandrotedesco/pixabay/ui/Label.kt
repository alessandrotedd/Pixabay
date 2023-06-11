package app.alessandrotedesco.pixabay.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Label(label: String, value: String) {
    Row {
        Text(text = "$label: ", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleSmall)
        Text(text = value)
    }
}

@Preview
@Composable
fun LabelPreview() {
    Label("Label", "Value")
}