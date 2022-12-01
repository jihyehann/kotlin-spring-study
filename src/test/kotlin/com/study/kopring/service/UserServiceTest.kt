package com.study.kopring.service

import com.study.kopring.domain.user.User
import com.study.kopring.domain.user.UserRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate
import java.util.*

class UserServiceTest : BehaviorSpec({
    val userRepository = mockk<UserRepository>()
    val userService = UserService(userRepository)

    Given("회원이 로그인한 경우") {
        val user = User("user1", "user1@email.com", "010-1234-1234", LocalDate.now(), 1L)

        every { userRepository.existsByEmail(user.email) } returns true
        every { userRepository.findById(user.id) } returns Optional.of(user)

        When("해당 회원의 정보를 불러오면") {
            val userInfo = userService.getUserInfo(user.id)

            Then("회원 정보를 확인할 수 있다.") {
                userInfo.email shouldBe user.email
                userInfo.birthday shouldBe user.birthday
                userInfo.name shouldBe user.name
            }
        }
    }

    Given("회원가입 정보를 올바르게 입력한 경우") {
        val request = RegisterUserRequest("user1", "010-1234-1234", "user1@email.com", LocalDate.now())

        every { userRepository.existsByEmail(any()) } returns false
        every { userRepository.save(any()) } returns request.toEntity()

        When("회원가입을 하면") {
            val createUser = userService.createUser(request)

            Then("회원이 추가된다") {
                createUser.name shouldBe request.name

            }
        }
    }

})
