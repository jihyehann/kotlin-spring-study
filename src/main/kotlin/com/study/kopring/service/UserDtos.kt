package com.study.kopring.service

import com.study.kopring.domain.user.User
import java.time.LocalDate
import javax.validation.constraints.Email
import javax.validation.constraints.Past
import javax.validation.constraints.Pattern

data class RegisterUserRequest(
    @field:Pattern(regexp = "[가-힣]{1,30}", message = "올바른 형식의 이름이어야 합니다")
    val name: String,

    @field:Pattern(regexp = "010-\\d{4}-\\d{4}", message = "올바른 형식의 전화번호여야 합니다")
    val phoneNumber: String,

    @field:Email
    val email: String,

    @field:Past
    val birthday: LocalDate,
) {
    fun toEntity(): User {
        return User(name, email, phoneNumber, birthday)
    }
}