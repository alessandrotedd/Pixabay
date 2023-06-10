package app.alessandrotedesco.pixabay.ui.section.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import app.alessandrotedesco.pixabay.apiservice.RemoteDataSourceRetrofit
import app.alessandrotedesco.pixabay.apiservice.Resource
import app.alessandrotedesco.pixabay.apiservice.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val api: RemoteDataSourceRetrofit): ViewModel() { // TODO example
    val pokemon = MutableLiveData<Pokemon>()

    fun getPokemon(name: String) = viewModelScope.launch {
        val response = api.getPokemon(name)
        if (response is Resource.Success) {
            pokemon.postValue(response.data)
        } else {
            pokemon.postValue(null) // TODO handle error
        }
    }
}
