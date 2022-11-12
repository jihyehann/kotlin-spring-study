package com.study.kopring.controller

import com.fasterxml.jackson.annotation.JsonInclude

data class ApiResponse<T>(
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val message: String? = null,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    val body: T? = null
) {
    companion object {
        fun error(message: String?): ApiResponse<Unit> = ApiResponse(message = message)

        fun <T> success(body: T?): ApiResponse<T> = ApiResponse(body = body)
    }
}