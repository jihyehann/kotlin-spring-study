package com.study.kopring.domain.todo

import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<Todo, Long> {

    fun findByUserId(userId: Long): List<Todo>;
}
