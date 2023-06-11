package app.alessandrotedesco.pixabay.injection

import android.content.Context
import app.alessandrotedesco.pixabay.apiservice.RemoteDataSourceRetrofit
import app.alessandrotedesco.pixabay.datastore.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltMainModule {
    @Singleton
    @Provides
    fun provideWebService(@ApplicationContext context: Context) = RemoteDataSourceRetrofit(context)

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager = DataStoreManager(context)
}