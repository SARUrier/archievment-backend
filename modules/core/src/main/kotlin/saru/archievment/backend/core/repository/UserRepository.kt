package saru.archievment.backend.core.repository

import org.springframework.stereotype.Repository
import saru.archievment.backend.core.data.User

@Repository
interface UserRepository : AbstractEntityRepository<User> {

    fun findByUsername(username: String): User?

}