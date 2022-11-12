package com.study.kopring.controller

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.annotations.ApiModelProperty

data class ApiResponse<T>(
    @ApiModelProperty(value = "오류 메시지")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val message: String? = null,

    @ApiModelProperty(value = "결과 데이터")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val body: T? = null
) {
    companion object {
        fun error(message: String?): ApiResponse<Unit> = ApiResponse(message = message)

        fun <T> success(body: T?): ApiResponse<T> = ApiResponse(body = body)
    }
}