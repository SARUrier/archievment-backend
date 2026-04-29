package saru.archievment.backend.core.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import saru.archievment.backend.core.data.User
import saru.archievment.backend.core.service.UserService

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
) {

    @GetMapping
    fun getUsers(): List<User> {
        return userService.findAll()
    }

}