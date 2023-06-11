package app.alessandrotedesco.pixabay.apiservice.model

data class ImageResponse(
    val hits: List<Image> = emptyList()
)

data class Image(
    val tags: String = "",
    val previewURL: String = "",
    val largeImageURL: String = "",
    val imageWidth: Int = 1,
    val imageHeight: Int = 1,
    val downloads: Int = 0,
    val likes: Int = 0,
    val comments: Int = 0,
    val user: String = "",
)
