# Pixabay Image Search

This is an Android application that uses the Pixabay API to search for images. It's built with Kotlin and Jetpack Compose.

## Features

- Search for images using keywords
- View image details
- Offline caching of images
- No results screen
- Dark theme
- Aspects ratio aware image loading

## Setup

To run this project, you will need to obtain an API key from Pixabay and add it to your local configuration.

1. Sign up for a free account on [Pixabay](https://pixabay.com/service/about/api/) to get your API key.
2. Create a new file named `local.properties` in the root directory of the project.
3. Add the following line to the `local.properties` file, replacing `YOUR_API_KEY` with your actual API key:

```properties
pixabayApiKey=YOUR_API_KEY
```

Sync your project and run the app.

## Libraries Used
- Jetpack Compose for the UI
- Retrofit for network requests
- Coil for image loading
- Moshi for JSON parsing
- Hilt for dependency injection

## License
This project is open source under the MIT license. See the LICENSE file for more information.