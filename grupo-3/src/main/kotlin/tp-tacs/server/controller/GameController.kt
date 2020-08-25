package server.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import server.models.CreateGameData

@RestController
@RequestMapping("/games")
class GameController {

    @PostMapping
    fun createUser(@RequestBody gameData: CreateGameData): ResponseEntity<String> {
        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun allGames(@RequestParam date: String, @RequestParam state: String): ResponseEntity<String> {
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/{id}")
    fun updateGameState(@PathVariable id: String, @RequestParam state: String): ResponseEntity<String> {
        return ResponseEntity.ok().build()
    }


}