package app.alessandrotedesco.pixabay.apiservice.model

import com.squareup.moshi.Json

data class ImageResponse(
    val total: Int = 0,
    val totalHits: Int = 0,
    val hits: List<Image> = emptyList()
)

data class Image(
    val id: Int = 0,
    val pageURL: String = "",
    val type: String = "",
    val tags: String = "",
    val previewURL: String = "",
    val previewWidth: Int = 0,
    val previewHeight: Int = 0,
    val webformatURL: String = "",
    val webformatWidth: Int = 0,
    val webformatHeight: Int = 0,
    val largeImageURL: String = "",
    val fullHDURL: String? = null,
    val imageURL: String? = null,
    val imageWidth: Int = 0,
    val imageHeight: Int = 0,
    val imageSize: Int = 0,
    val views: Int = 0,
    val downloads: Int = 0,
    val likes: Int = 0,
    val comments: Int = 0,
    val vectorURL: String? = null,
    @Json(name = "user_id") val userId: Int = 0,
    val user: String = "",
    val userImageURL: String = ""
)
