package com.study.kopring.service

import com.study.kopring.domain.user.User
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate
import javax.validation.constraints.Email
import javax.validation.constraints.Past
import javax.validation.constraints.Pattern

@ApiModel(value = "회원 정보")
data class RegisterUserRequest(
    @ApiModelProperty(value = "회원 이름", example = "홍길동")
    @field:Pattern(regexp = "[가-힣]{1,30}", message = "올바른 형식의 이름이어야 합니다")
    val name: String,

    @ApiModelProperty(value = "핸드폰 번호", example = "010-1234-5678")
    @field:Pattern(regexp = "010-\\d{4}-\\d{4}", message = "올바른 형식의 전화번호여야 합니다")
    val phoneNumber: String,

    @ApiModelProperty(value = "이메일 주소", example = "user@email.com")
    @field:Email
    val email: String,

    @ApiModelProperty(value = "생년월일")
    @field:Past
    val birthday: LocalDate,
) {
    fun toEntity(): User {
        return User(name, email, phoneNumber, birthday)
    }
}

data class RegisterUserResponse(
    @ApiModelProperty(value = "토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0IiwiZXhwIjoxNjY4NDk5MDE3fQ.Zwb9To6T95xqdczpN93TT9uBBPu4nIe9PAGoZdKZDVg")
    val accessToken: String,
)
