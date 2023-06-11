package app.alessandrotedesco.pixabay.ui.section.details

import androidx.lifecycle.ViewModel
import app.alessandrotedesco.pixabay.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(dataStore: DataStoreManager) : ViewModel() {
    val image = dataStore.cachedImage
}
