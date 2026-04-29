package saru.archievment.backend.core.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import saru.archievment.backend.core.data.User
import saru.archievment.backend.core.repository.UserRepository

@Service
class UserService(
    repository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) : AbstractEntityService<User, UserRepository>(repository) {

    final fun save() {
        throw UnsupportedOperationException("Use createUser or updatePassword instead of save directly to ensure password is encoded")
    }

    fun createUser(username: String, rawPassword: String): User {
        val encodedPassword = passwordEncoder.encode(rawPassword)
        require(!encodedPassword.isNullOrBlank()) { "Failed to encode password for user '$username'" }

        val user = User(
            username = username,
            password = encodedPassword
        )
        return save(user)
    }

    fun updatePassword(user: User, newRawPassword: String): User {
        val encodedPassword = passwordEncoder.encode(newRawPassword)
        require(!encodedPassword.isNullOrBlank()) { "Failed to encode password for user '${user.username}'" }
        user.password = encodedPassword
        return save(user)
    }

    fun validateLogin(username: String, rawPassword: String): Boolean {
        val user = repository.findByUsername(username) ?: return false
        return passwordEncoder.matches(rawPassword, user.password)
    }

    fun findByUsername(username: String): User? {
        return repository.findByUsername(username)
    }

}