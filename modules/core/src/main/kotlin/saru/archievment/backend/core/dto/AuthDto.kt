package saru.archievment.backend.core.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class LoginRequest @JsonCreator constructor(
    @JsonProperty("username")
    val username: String,
    @JsonProperty("password")
    val password: String,
)

data class AuthUserDto @JsonCreator constructor(
    @JsonProperty("username")
    val username: String,
)