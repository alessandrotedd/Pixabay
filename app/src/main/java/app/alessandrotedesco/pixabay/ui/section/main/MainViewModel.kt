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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: RemoteDataSourceRetrofit
): ViewModel() {
    val images = MutableLiveData<ImageResponse?>()
    var query: MutableState<String> = mutableStateOf("fruits")
    val showDialog: MutableState<Boolean> = mutableStateOf(false)
    val selectedImage: MutableState<Image?> = mutableStateOf(null)

    fun searchImages(query: String) = viewModelScope.launch {
        val response = api.searchImages(query)
        if (response is Resource.Success) {
            images.postValue(response.data)
        } else {
            images.postValue(null)
        }
    }
}
