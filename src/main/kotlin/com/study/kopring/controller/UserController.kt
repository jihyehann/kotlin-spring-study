package com.study.kopring.controller

import com.study.kopring.service.RegisterUserRequest
import com.study.kopring.service.RegisterUserResponse
import com.study.kopring.service.UserService
import com.study.kopring.utils.JwtTokenProvider
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponses
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Api(tags = ["회원"])
@RequestMapping("/user")
@RestController
class UserController(
    private val userService: UserService,
    private val tokenProvider: JwtTokenProvider
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @ApiOperation(value = "회원가입 API")
    @ApiResponses(
        io.swagger.annotations.ApiResponse(code = 200, message = "회원가입 성공", response = ApiResponse::class),
        io.swagger.annotations.ApiResponse(code = 400, message = "회원가입 실패, 잘못된 입력값")
    )
    @PostMapping("/register")
    fun register(@RequestBody @Valid request: RegisterUserRequest): ApiResponse<RegisterUserResponse> {
        log.info("request = $request")
        val token = tokenProvider.createToken(userService.createUser(request).id)
        return ApiResponse.success(RegisterUserResponse(token))
    }
}