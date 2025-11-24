package com.macaneta.mahungu.data.model.error

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val status: String = Status.ERROR.status,
    val code: String,
    override val message: String,
): Exception(message)
