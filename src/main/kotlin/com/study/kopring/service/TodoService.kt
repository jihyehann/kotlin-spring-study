package com.study.kopring.service

import com.study.kopring.domain.todo.Todo
import com.study.kopring.domain.todo.TodoRepository
import com.study.kopring.domain.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoService(
    private val todoRepository: TodoRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun insertTodo(userId: Long, request: PostTodoRequest) {
        val user = userRepository.findById(userId)
            .orElseThrow() { IllegalArgumentException("존재하지 않는 회원입니다.") }
        todoRepository.save(Todo(content = request.content, user = user))
    }

    fun getTodos(userId: Long): List<GetTodoResponse> {
        return todoRepository.findByUserId(userId).map(::GetTodoResponse)
    }

}

