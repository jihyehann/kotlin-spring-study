package com.study.kopring.service

import com.study.kopring.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository
) {

    // 회원가입
    fun createUser(request: RegisterUserRequest) {
        require (!userRepository.existsByEmail(request.email)) {"이미 가입된 이메일입니다."}
        val user = userRepository.save(request.toEntity())

    }
}