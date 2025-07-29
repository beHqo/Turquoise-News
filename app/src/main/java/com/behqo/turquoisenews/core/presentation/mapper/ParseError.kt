package com.behqo.turquoisenews.core.presentation.mapper

import com.behqo.turquoisenews.R
import com.behqo.turquoisenews.core.domain.model.DatabaseError
import com.behqo.turquoisenews.core.domain.model.Error
import com.behqo.turquoisenews.core.domain.model.NetworkError

fun Error.toLocalizedMessage(): Int = when (this) {
    NetworkError.BAD_REQUEST -> R.string.network_error_bad_request
    NetworkError.UNAUTHORIZED -> R.string.network_error_unauthorized
    NetworkError.TOO_MANY_REQUESTS -> R.string.network_error_too_many_requests
    NetworkError.SERVER_ERROR -> R.string.network_error_server_error
    NetworkError.REQUEST_TIMEOUT -> R.string.network_error_request_timeout
    NetworkError.SOCKET_TIMEOUT -> R.string.network_error_socket_timeout
    NetworkError.CONNECTION_TIMEOUT -> R.string.network_error_connection_timeout
    NetworkError.NO_INTERNET -> R.string.network_error_no_internet
    NetworkError.SERIALIZATION -> R.string.network_error_serialization
    NetworkError.UNKNOWN_IO -> R.string.network_error_unknown_io
    DatabaseError.NO_SUCH_ELEMENT -> R.string.database_error_no_such_element
    DatabaseError.UNKNOWN_IO -> R.string.database_error_unknown_io
    else -> R.string.exhausting_the_when_statement
}