package saru.archievment.backend

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserController {

    @GetMapping("/users")
    fun getUsers(): List<UserProfile> {
        return listOf(UserProfile(
            firstName = "John",
            lastName = "Doe",
            dateOfBirth = java.time.LocalDate.of(1990, 1, 1)
        ))
    }

    @GetMapping("/user")
    fun getUser(): UserProfile {
        return UserProfile(
            firstName = "John",
            lastName = "Doe",
            dateOfBirth = java.time.LocalDate.of(1990, 1, 1)
        )
    }

}