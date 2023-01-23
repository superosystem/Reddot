package com.reddot.common

import java.util.Date

data class ErrorMessage<T>(
    var code: Int,
    var status: String,
    var timestamp: Date,
    var data: T
)