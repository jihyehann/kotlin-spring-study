package com.study.kopring.config

import com.study.kopring.utils.JwtTokenProvider
import com.sun.security.auth.UserPrincipal
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
        val token = extractBearerToken(request)
        if (token == null) {
            filterChain.doFilter(request, response)
            return
        }

        val userId = tokenProvider.getSubject(token)
        val authentication = UsernamePasswordAuthenticationToken(UserPrincipal(userId), null, NO_AUTHORITIES)
        SecurityContextHolder.getContext().authentication = authentication

        filterChain.doFilter(request, response)
    }

    private fun extractBearerToken(request: HttpServletRequest): String? {
        val authorization = request.getHeader(AUTHORIZATION) ?: return null
        if (!authorization.startsWith(BEARER)) {
            throw IllegalArgumentException("올바른 형식의 JWT가 아닙니다.")
        }
        return authorization.substring(7)
    }


}