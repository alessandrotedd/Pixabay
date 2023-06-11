package app.alessandrotedesco.pixabay.apiservice

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

abstract class BaseRepo {
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    Resource.Success(data = response.body()!!)
                } else {
                    val errorMessage = response.errorBody()?.string()
                    Timber.e("OkHttp API error response: $errorMessage")
                    Resource.Error(errorMessage = errorMessage ?: "Something went wrong")
                }

            } catch (e: HttpException) {
                Resource.Error(errorMessage = e.message ?: "Something went wrong, code: ${e.code()}")
            } catch (e: IOException) {
                Resource.Error(errorMessage = "Please check your network connection: ${e.message}")
            } catch (e: Exception) {
                Resource.Error(errorMessage = "Something went wrong: ${e.message}")
            }
        }
    }
}