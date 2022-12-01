package com.study.kopring.domain.user

import RepositoryTest
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.optional.shouldBePresent
import io.kotest.matchers.shouldBe
import java.time.LocalDate

@RepositoryTest
class UserRepositoryTest(
    private val userRepository: UserRepository
) : BehaviorSpec({

    Given("존재하는 회원 id가 주어진 경우") {
        val user = userRepository.save(User("user1", "user1@email.com", "010-1234-1234", LocalDate.now()))

        When("해당 id로 회원을 조회하면") {
            val optionalUser = userRepository.findById(user.id)

            Then("해당 id의 회원 정보가 조회된다") {
                optionalUser.shouldBePresent()
                optionalUser.get().id shouldBe user.id
                optionalUser.get().name shouldBe user.name
                optionalUser.get().email shouldBe user.email
                optionalUser.get().phoneNumber shouldBe user.phoneNumber
                optionalUser.get().birthday shouldBe user.birthday
            }
        }
    }
})
