package app.alessandrotedesco.pixabay.apiservice.interceptors

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor

class OfflineCacheInterceptor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()

        if (!isNetworkAvailable(context)) {
            request = request.newBuilder()
                .removeHeader("Cache-Control")
                .addHeader("Cache-Control", "public, max-stale=" + 60 * 60 * 24 * 28) // 4 weeks
                .build()
        }

        return chain.proceed(request)
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
