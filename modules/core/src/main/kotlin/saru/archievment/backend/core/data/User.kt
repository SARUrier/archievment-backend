package saru.archievment.backend.core.data

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(
    name = "application_user",
    indexes = [Index(name = "idx_username", columnList = "username")],
    uniqueConstraints = [UniqueConstraint(name = "uk_username", columnNames = ["username"])]
)
class User(
    @NaturalId
    @Column(nullable = false, length = 50)
    var username: String,
    @Column(nullable = false, length = 60)
    var password: String,
    @CreationTimestamp
    var createdAt: LocalDateTime? = null,
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null,
) : AbstractEntity()
