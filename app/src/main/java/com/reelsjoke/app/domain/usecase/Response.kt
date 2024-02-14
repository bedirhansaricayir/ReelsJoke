package com.reelsjoke.app.domain.usecase


/**
 * Created by bedirhansaricayir on 27.01.2024.
 */

sealed interface Response<out T> {
    class Success<T>(val data: T?) : Response<T>
    class Error<T>(val errorMessage: String) : Response<T>
    object Loading : Response<Nothing>
}

fun <T> Response<T>.successOr(fallback: T): T {
    return (this as? Response.Success<T>)?.data ?: fallback
}