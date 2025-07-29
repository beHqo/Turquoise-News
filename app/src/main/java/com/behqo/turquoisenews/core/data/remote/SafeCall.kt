package com.behqo.turquoisenews.core.data.remote

import android.util.Log
import com.behqo.turquoisenews.core.domain.model.NetworkError
import com.behqo.turquoisenews.core.domain.model.Result
import io.ktor.client.call.body
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.SerializationException
import okio.IOException
import java.net.UnknownHostException

suspend inline fun <reified T> makeHttpRequestSafely(
    logTag: String, block: suspend () -> HttpResponse
): Result<T> = try {
    val response = block()

    response.toResult()
} catch (e: HttpRequestTimeoutException) {
    Log.e(logTag, "HTTP request timed out has exceeded", e)

    Result.Failure(NetworkError.REQUEST_TIMEOUT)
} catch (e: ConnectTimeoutException) {
    Log.e(logTag, "Connection timed out has exceeded", e)

    Result.Failure(NetworkError.CONNECTION_TIMEOUT)
} catch (e: SocketTimeoutException) {
    Log.e(logTag, "Socket timed out has exceeded", e)

    Result.Failure(NetworkError.SOCKET_TIMEOUT)
} catch (e: SerializationException) {
    Log.e(logTag, "Failed to deserialize data", e)

    Result.Failure(NetworkError.SERIALIZATION)
} catch (e: UnknownHostException) {
    Log.e(logTag, "Failed to make HTTP request due to IO error", e)

    Result.Failure(NetworkError.NO_INTERNET)
} catch (e: IOException) {
    Log.e(logTag, "Failed to make HTTP request due to an IO error", e)

    Result.Failure(NetworkError.NO_INTERNET)
}

suspend inline fun <reified T> HttpResponse.toResult(): Result<T> = when (status.value) {
    200 -> Result.Success(body<T>())
    400 -> Result.Failure(NetworkError.BAD_REQUEST)
    401 -> Result.Failure(NetworkError.UNAUTHORIZED)
    429 -> Result.Failure(NetworkError.TOO_MANY_REQUESTS)
    500 -> Result.Failure(NetworkError.SERVER_ERROR)
    else -> Result.Failure(NetworkError.UNKNOWN_IO)
}