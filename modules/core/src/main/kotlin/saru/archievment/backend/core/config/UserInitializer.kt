package saru.archievment.backend.core.config

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import saru.archievment.backend.core.service.UserService

private const val ADMIN = "admin"
private const val USER = "user"

@Component
class UserInitializer(
    private val userService: UserService,
) {

    @EventListener(ApplicationReadyEvent::class)
    fun initBasicUsers() {
        userService.findByUsername(ADMIN) ?: userService.createUser(
            ADMIN, "${ADMIN}pw"
        )

        userService.findByUsername(USER) ?: userService.createUser(
            USER, "${USER}pw"
        )

    }

}