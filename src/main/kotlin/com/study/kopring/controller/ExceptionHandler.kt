package com.study.kopring.controller

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    // null 혹은 형식 오류
    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.error("message=${ex.message}")
        val message = when (val exception = ex.cause) {
            is MissingKotlinParameterException -> "${exception.parameter.name.orEmpty()}는 널이어서는 안됩니다"
            is InvalidFormatException -> "${exception.path.last().fieldName.orEmpty()}는 올바른 형식이어야 합니다"
            else -> exception?.message?:"올바르지 않은 요청입니다."
        }
        return ResponseEntity.badRequest().body(ApiResponse.error(message))
    }

    // validation 오류
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.error("message=${ex.message}")
        val message = ex.bindingResult.fieldErrors.joinToString(", ") { fieldError -> "${fieldError.field}: ${fieldError.defaultMessage.orEmpty()}" }
        return ResponseEntity.badRequest().body(ApiResponse.error(message))
    }

    // 기타 오류
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(ex: Exception): ApiResponse<Unit> {
        logger.error("message=${ex.message}")
        return ApiResponse.error(ex.message)
    }


}
