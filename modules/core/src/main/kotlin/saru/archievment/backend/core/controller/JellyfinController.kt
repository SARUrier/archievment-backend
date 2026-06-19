package saru.archievment.backend.core.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import saru.archievment.backend.core.config.JellyfinProperties
import saru.archievment.backend.core.dto.JellyfinTokenDto

@RestController
@RequestMapping("/api/jellyfin")
class JellyfinController(
    private val jellyfinProperties: JellyfinProperties,
) {

    @GetMapping("/token")
    fun jellyfinToken(authentication: Authentication?): ResponseEntity<JellyfinTokenDto> {
        if (authentication == null || !authentication.isAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
        val apiKey = jellyfinProperties.apiKey
            ?: return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build()

        return ResponseEntity.ok(JellyfinTokenDto(apiKey))
    }

}