package com.ireland.ager.config

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass
import kotlinx.serialization.*

/**
 * @Class : BaseEntity
 * @Description : 도메인에 대한 공통 엔티티
 */
@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
@Serializable
abstract class BaseEntity {
    @CreatedDate
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @Column(name = "created_at", updatable = false)
    private val createdAt: LocalDateTime? = null

    @LastModifiedDate
    @JsonSerialize(using = LocalDateTimeSerializer::class)
    @JsonDeserialize(using = LocalDateTimeDeserializer::class)
    @Column(name = "updated_at", updatable = true)
    private val updatedAt: LocalDateTime? = null
}