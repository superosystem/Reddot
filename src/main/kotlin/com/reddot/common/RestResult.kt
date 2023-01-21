package com.reddot.common

data class RestResult<T>(
    val code: Int,
    val status: String,
    val data: T
)
