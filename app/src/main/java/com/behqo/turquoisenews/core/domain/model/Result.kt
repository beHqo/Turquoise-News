package com.behqo.turquoisenews.core.domain.model

sealed interface Result<out D> {
    data class Success<out D>(val data: D) : Result<D>
    data class Failure(val error: Error) : Result<Nothing>
    object Loading : Result<Nothing>
}

/**
 * If `this` is a `Success<D>`, apply [transformation] to its data and wrap in `Success<R>`.
 * Otherwise (Loading or Error), return the same instance (typed as `Result<R>`).
 */
inline fun <D, R> Result<D>.transform(transformation: (D) -> R): Result<R> = when (this) {
    is Result.Success -> Result.Success(transformation(data))
    is Result.Failure -> this
    Result.Loading -> Result.Loading
}