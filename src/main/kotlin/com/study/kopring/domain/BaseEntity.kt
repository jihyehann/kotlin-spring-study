package com.study.kopring.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.sql.Timestamp
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    var createdDate: Timestamp? = null

    @LastModifiedDate
    @Column(updatable = true)
    var lastModifiedDate: Timestamp? = null
}
