package com.study.kopring.controller

import com.study.kopring.service.GetTodoResponse
import com.study.kopring.service.PostTodoRequest
import com.study.kopring.service.TodoService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore

@RequestMapping("/todo")
@RestController
class TodoController(
    private val todoService: TodoService
) {

    @PostMapping
    fun addTodo(
        @ApiIgnore @AuthenticationPrincipal principal: Long?,
        @RequestBody request: PostTodoRequest
    ): ApiResponse<Unit> {
        requireNotNull(principal) {"JWT를 입력해주세요"}
        todoService.insertTodo(principal, request)
        return ApiResponse.success(null)
    }

    @GetMapping
    fun getTodo(@ApiIgnore @AuthenticationPrincipal principal: Long?): ApiResponse<List<GetTodoResponse>> {
        requireNotNull(principal) {"JWT를 입력해주세요"}
        return ApiResponse.success(todoService.getTodos(principal))
    }

}
