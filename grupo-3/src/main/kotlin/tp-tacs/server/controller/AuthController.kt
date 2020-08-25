package server.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import server.models.LogIn
import server.models.SingUp

@RestController
@RequestMapping("/auth")
class AuthController {

    @PostMapping("/signUp")
    fun singUp(@RequestBody signUpData: SingUp): ResponseEntity<String> {
        return ResponseEntity.ok().build()
    }

    @PostMapping("/logIn")
    fun logIn(@RequestBody logInData: LogIn) : ResponseEntity<String> {
        return ResponseEntity.ok().build()
    }

    @PostMapping("/logOut")
    fun logOut() : ResponseEntity<String> {
        return ResponseEntity.ok().build()
    }
}