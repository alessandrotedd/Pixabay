package app.alessandrotedesco.pixabay.datastore

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    const val CACHED_IMAGE = "cached_image"

    val IMAGE_LIKES = intPreferencesKey("IMAGE_LIKES")
    val IMAGE_DOWNLOADS = intPreferencesKey("IMAGE_DOWNLOADS")
    val IMAGE_COMMENT = intPreferencesKey("IMAGE_COMMENT")
    val IMAGE_TAGS = stringPreferencesKey("IMAGE_TAGS")
    val IMAGE_USER = stringPreferencesKey("IMAGE_USER")
    val IMAGE_URL = stringPreferencesKey("IMAGE_URL")
}