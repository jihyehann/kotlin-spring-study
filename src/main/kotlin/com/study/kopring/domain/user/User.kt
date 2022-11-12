package com.study.kopring.domain.user

import com.study.kopring.domain.BaseEntity
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class User(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val birthday: LocalDate,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseEntity() {
}