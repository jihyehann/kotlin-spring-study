package com.study.kopring.service

import com.study.kopring.domain.todo.Todo

data class PostTodoRequest(
    val content: String
)

data class GetTodoResponse(
    val todoId: Long,
    val content: String,
    val complete: Boolean
) {
    constructor(todo: Todo) : this(todo.id, todo.content, todo.complete)
}
