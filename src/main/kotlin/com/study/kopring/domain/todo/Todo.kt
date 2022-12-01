package com.study.kopring.domain.todo

import com.study.kopring.domain.BaseEntity
import com.study.kopring.domain.user.User
import javax.persistence.*

@Entity
class Todo(
    val content: String,
    val complete: Boolean = false,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : BaseEntity()
