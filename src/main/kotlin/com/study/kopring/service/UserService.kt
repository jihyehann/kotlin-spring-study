package com.study.kopring.service

import com.study.kopring.domain.user.User
import com.study.kopring.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository
) {

    // 회원가입
    fun createUser(request: RegisterUserRequest): User {
        require (!userRepository.existsByEmail(request.email)) {"이미 가입된 이메일입니다."}
        return userRepository.save(request.toEntity())
    }

    fun getUserInfo(userId: Long): GetUserResponse {
        val user = userRepository.findById(userId)
            .orElseThrow(){IllegalArgumentException("존재하지 않는 회원입니다.")}
        return GetUserResponse(user)
    }
}
