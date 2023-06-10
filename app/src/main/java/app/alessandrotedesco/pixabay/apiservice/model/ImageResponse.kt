package app.alessandrotedesco.pixabay.apiservice.model

import com.squareup.moshi.Json

data class ImageResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<Image>
)

data class Image(
    val id: Int,
    val pageURL: String,
    val type: String,
    val tags: String,
    val previewURL: String,
    val previewWidth: Int,
    val previewHeight: Int,
    val webformatURL: String,
    val webformatWidth: Int,
    val webformatHeight: Int,
    val largeImageURL: String,
    val fullHDURL: String?,
    val imageURL: String?,
    val imageWidth: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val views: Int,
    val downloads: Int,
    val likes: Int,
    val comments: Int,
    val vectorURL: String?,
    @Json(name = "user_id") val userId: Int,
    val user: String,
    val userImageURL: String
)
