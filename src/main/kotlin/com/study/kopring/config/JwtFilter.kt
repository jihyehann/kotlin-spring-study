package com.study.kopring.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.JSONPObject
import com.study.kopring.controller.ApiResponse
import com.study.kopring.utils.JwtTokenProvider
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils.NO_AUTHORITIES
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val BEARER: String = "Bearer"

@Component
class JwtFilter(
    private val tokenProvider: JwtTokenProvider,
) : OncePerRequestFilter() {


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = extractBearerToken(request)
            if (token == null) {
                filterChain.doFilter(request, response)
                return
            }

            val userId = tokenProvider.getSubject(token).toLong()
            val authentication = UsernamePasswordAuthenticationToken(userId, null, NO_AUTHORITIES)
            SecurityContextHolder.getContext().authentication = authentication

            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            setErrorResponse(response, e.message)
        }

    }

    private fun extractBearerToken(request: HttpServletRequest): String? {
        val authorization = request.getHeader(AUTHORIZATION) ?: return null
        if (!authorization.startsWith(BEARER)) {
            throw IllegalArgumentException("올바른 형식의 JWT가 아닙니다.")
        }
        return authorization.substring(7)
    }

    private fun setErrorResponse(response: HttpServletResponse, errorMessage: String?) {
        response.contentType = "application/json;charset=UTF-8"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer.print(ObjectMapper().writeValueAsString(mapOf(Pair("message", errorMessage))))
    }
}