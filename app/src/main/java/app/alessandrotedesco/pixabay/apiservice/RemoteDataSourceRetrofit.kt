package app.alessandrotedesco.pixabay.apiservice

import android.content.Context
import app.alessandrotedesco.pixabay.BuildConfig
import app.alessandrotedesco.pixabay.apiservice.interceptors.HeaderInterceptor
import app.alessandrotedesco.pixabay.apiservice.interceptors.OfflineCacheInterceptor
import app.alessandrotedesco.pixabay.apiservice.model.ImageResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class RemoteDataSourceRetrofit(context: Context) : BaseRepo() {
    private var client: Service

    init {
        val headerInterceptor = HeaderInterceptor()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else HttpLoggingInterceptor.Level.NONE

        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())

        val oAuthClient = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(OfflineCacheInterceptor(context))
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .cache(cache)
            .build()

        val moshiBuilder = MoshiConverterFactory.create(
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        )

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.ENDPOINT)
            .addConverterFactory(moshiBuilder)
            .client(oAuthClient)
            .build()

        client = retrofit.create(Service::class.java)
    }

    suspend fun searchImages(name: String): Resource<ImageResponse> = safeApiCall {
        client.searchImages(name)
    }
}