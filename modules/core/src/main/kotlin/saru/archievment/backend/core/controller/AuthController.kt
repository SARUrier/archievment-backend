package saru.archievment.backend.core.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.web.bind.annotation.*
import saru.archievment.backend.core.dto.AuthUserDto
import saru.archievment.backend.core.dto.LoginRequestDto

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
) {
    private val securityContextRepository = HttpSessionSecurityContextRepository()

    @PostMapping("/login")
    fun login(
        @RequestBody
        request: LoginRequestDto,
        servletRequest: HttpServletRequest,
        servletResponse: HttpServletResponse,
    ): ResponseEntity<AuthUserDto> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )
        val context = SecurityContextHolder.createEmptyContext()
        context.authentication = authentication
        SecurityContextHolder.setContext(context)
        securityContextRepository.saveContext(context, servletRequest, servletResponse)

        val username = (authentication.principal as UserDetails).username
        return ResponseEntity.ok(AuthUserDto(username))
    }

    @PostMapping("/logout")
    fun logout(
        servletRequest: HttpServletRequest,
    ): ResponseEntity<Unit> {
        servletRequest.getSession(false)?.invalidate()
        SecurityContextHolder.clearContext()
        return ResponseEntity.ok().build()
    }

    @GetMapping("/me")
    fun me(authentication: Authentication?): ResponseEntity<AuthUserDto> {
        if (authentication == null || !authentication.isAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
        return ResponseEntity.ok(AuthUserDto(authentication.name))
    }
}