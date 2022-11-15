package com.study.kopring.utils

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.lang.Exception
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    private val key: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256),
    private val accessTokenExpireTime: Int = 1000 * 60 * 60
) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun createToken(userId: Long): String {
        val now = Date()
        val expiration = Date(now.time + accessTokenExpireTime)

        return Jwts.builder()
            .setSubject(userId.toString())
            .setExpiration(expiration)
            .signWith(key)
            .compact()
    }

    fun getSubject(token: String): String {
        return try {
            getClaims(token).body.subject
        } catch (e: ExpiredJwtException) {
            log.info("만료된 토큰")
            throw IllegalArgumentException("만료된 토큰")
        } catch (e: Exception) {
            log.info("올바르지 않은 토큰")
            throw IllegalArgumentException("올바르지 않은 토큰")
        }
    }

    private fun getClaims(token: String) = Jwts.parserBuilder().setSigningKey(key.encoded).build().parseClaimsJws(token)



}