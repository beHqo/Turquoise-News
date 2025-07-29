package com.behqo.turquoisenews.core.domain.model

enum class NetworkError : Error {
    BAD_REQUEST,
    UNAUTHORIZED,
    TOO_MANY_REQUESTS,
    SERVER_ERROR,
    REQUEST_TIMEOUT,
    SOCKET_TIMEOUT,
    CONNECTION_TIMEOUT,
    NO_INTERNET,
    SERIALIZATION,
    UNKNOWN_IO
}