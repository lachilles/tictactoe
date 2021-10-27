package lachilles.games.tictactoe

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TicTacToeController {

    @PostMapping("/createGame")
    fun createGame(): GameResponse {
        return GameResponse()
    }

}
