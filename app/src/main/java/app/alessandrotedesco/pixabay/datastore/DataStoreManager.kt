package app.alessandrotedesco.pixabay.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import app.alessandrotedesco.pixabay.apiservice.model.Image
import app.alessandrotedesco.pixabay.datastore.PreferencesKeys.IMAGE_COMMENT
import app.alessandrotedesco.pixabay.datastore.PreferencesKeys.IMAGE_DOWNLOADS
import app.alessandrotedesco.pixabay.datastore.PreferencesKeys.IMAGE_LIKES
import app.alessandrotedesco.pixabay.datastore.PreferencesKeys.IMAGE_TAGS
import app.alessandrotedesco.pixabay.datastore.PreferencesKeys.IMAGE_URL
import app.alessandrotedesco.pixabay.datastore.PreferencesKeys.IMAGE_USER
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(PreferencesKeys.CACHED_IMAGE)

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.dataStore
    private val coroutineScope = CoroutineScope(Job())

    suspend fun cacheImage(image: Image) {
        dataStore.edit { preferences ->
            preferences[IMAGE_LIKES] = image.likes
            preferences[IMAGE_DOWNLOADS] = image.downloads
            preferences[IMAGE_COMMENT] = image.comments
            preferences[IMAGE_TAGS] = image.tags
            preferences[IMAGE_USER] = image.user
            preferences[IMAGE_URL] = image.largeImageURL
        }
    }

    val cachedImage: StateFlow<Image> = dataStore.data.map { preferences ->
        Image(
            likes = preferences[IMAGE_LIKES] ?: 0,
            downloads = preferences[IMAGE_DOWNLOADS] ?: 0,
            comments = preferences[IMAGE_COMMENT] ?: 0,
            tags = preferences[IMAGE_TAGS] ?: "",
            user = preferences[IMAGE_USER] ?: "",
            largeImageURL = preferences[IMAGE_URL] ?: ""
        )
    }.stateIn(coroutineScope, SharingStarted.Lazily, Image())
}