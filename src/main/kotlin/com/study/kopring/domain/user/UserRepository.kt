package com.study.kopring.domain.user

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository
    : JpaRepository<User, Long>, UserRepositoryCustom {
    fun existsByEmail(email: String): Boolean
}

interface UserRepositoryCustom {

}

class UserRepositoryImpl (
    private val queryFactory: JPAQueryFactory
) : UserRepositoryCustom {
}
