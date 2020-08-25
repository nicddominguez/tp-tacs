package server.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import server.models.CreateGameData

@RestController
@RequestMapping("/games")
class GameController {

    @PostMapping
    fun createGame(@RequestBody gameData: CreateGameData): ResponseEntity<String> {
        return ResponseEntity.ok().build()
    }

}