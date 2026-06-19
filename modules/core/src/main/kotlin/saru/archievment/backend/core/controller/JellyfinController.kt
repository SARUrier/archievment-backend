package saru.archievment.backend.core.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import saru.archievment.backend.core.config.JellyfinProperties
import saru.archievment.backend.core.dto.JellyfinTokenDto
import tools.jackson.databind.ObjectMapper
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

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
        val jellyfinUsername = jellyfinProperties.username
            ?: return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build()
        val jellyfinPassword = jellyfinProperties.password
            ?: return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build()

        val client = HttpClient.newHttpClient()
        val body = """{"Username":"$jellyfinUsername","Pw":"$jellyfinPassword"}"""

        val request = HttpRequest.newBuilder()
            .uri(URI.create("http://jellyfin:8096/jellyfin/Users/AuthenticateByName"))
            .header("Content-Type", "application/json")
            .header(
                "X-Emby-Authorization",
                """MediaBrowser Client="Archievment", Device="Server", DeviceId="archievment-server", Version="1.0.0", Token="$apiKey""""
            )
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != 200) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build()
        }

        val json = ObjectMapper().readTree(response.body())
        val accessToken = json.get("AccessToken").stringValue()

        return ResponseEntity.ok(JellyfinTokenDto(accessToken))
    }

}