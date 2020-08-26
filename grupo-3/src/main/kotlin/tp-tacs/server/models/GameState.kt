package server.models

enum class GameState(val state: String) {
    INPROGRESS("INPROGRESS"),
    FINISH("FINISH"),
    CANCEL("CANCEL")
}