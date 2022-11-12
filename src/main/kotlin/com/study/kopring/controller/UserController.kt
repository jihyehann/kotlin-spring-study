package com.study.kopring.controller

import com.study.kopring.service.RegisterUserRequest
import com.study.kopring.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RequestMapping("/user")
@RestController
class UserController(
    private val userService: UserService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("/register")
    fun register(@RequestBody @Valid request: RegisterUserRequest): ApiResponse<String> {
        log.info("request = $request")
        userService.createUser(request)
        return ApiResponse.success(null)
    }
}