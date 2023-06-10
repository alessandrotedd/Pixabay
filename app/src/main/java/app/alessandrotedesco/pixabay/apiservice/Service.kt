package app.alessandrotedesco.pixabay.apiservice

import app.alessandrotedesco.pixabay.BuildConfig
import app.alessandrotedesco.pixabay.apiservice.model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("/api/")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("key") key: String = BuildConfig.PIXABAY_API_KEY,
        @Query("image_type") imageType: String = "photo"
    ): Response<ImageResponse>
}
