package saru.archievment.backend.core.data

import jakarta.persistence.*

@Entity
@Table(name = "application_user")
class User(
    var username: String,
    var password: String,
) : AbstractEntity()
