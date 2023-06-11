package app.alessandrotedesco.pixabay.ui.section.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.alessandrotedesco.pixabay.apiservice.RemoteDataSourceRetrofit
import app.alessandrotedesco.pixabay.apiservice.Resource
import app.alessandrotedesco.pixabay.apiservice.model.Image
import app.alessandrotedesco.pixabay.apiservice.model.ImageResponse
import app.alessandrotedesco.pixabay.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: RemoteDataSourceRetrofit,
    private val dataStore: DataStoreManager
): ViewModel() {
    val images = MutableLiveData<List<Image>?>()
    val onError = mutableStateOf(false)
    var query: MutableState<String> = mutableStateOf("fruits")

    fun searchImages(query: String) = viewModelScope.launch {
        val response = api.searchImages(query)
        if (response is Resource.Success) {
            images.postValue((response.data as ImageResponse).hits)
        } else {
            onError.value = true
        }
    }

    fun cacheImage(image: Image) = viewModelScope.launch {
        dataStore.cacheImage(image)
    }
}
