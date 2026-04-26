package saru.archievment.backend.core.data

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController {

    @GetMapping("/list")
    fun getUsers(): List<User> {
        return listOf(User("user1", "password1"), User("user2", "password2"))
    }

}