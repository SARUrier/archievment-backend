package saru.archievment.backend.core.dto

data class LoginRequest(
    val username: String,
    val password: String,
)

data class AuthUserDto(
    val username: String,
)