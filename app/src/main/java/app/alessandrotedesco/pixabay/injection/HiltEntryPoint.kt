package app.alessandrotedesco.pixabay.injection

import app.alessandrotedesco.pixabay.apiservice.RemoteDataSourceRetrofit
import app.alessandrotedesco.pixabay.datastore.DataStoreManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface HiltEntryPoint {
    fun webService(): RemoteDataSourceRetrofit
    fun dataStoreManager(): DataStoreManager
}