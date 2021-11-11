package lachilles.games.tictactoe

import lachilles.games.tictactoe.service.InvalidPlayerException
import lachilles.games.tictactoe.service.TicTacToeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
class TicTacToeController {

    @Autowired
    lateinit var service: TicTacToeService

    @PostMapping("/createGame")
    fun createGame(): GameResponse {
        return service.createNewGame()
    }

    @PostMapping("/createPlayer/{gameId}")
    fun createPlayer(@PathVariable("gameId") gameId: String, @RequestParam playerName: String): GameResponse {
        // business logic:
        //   look up game by id
        //   add player to the game
        //   return result, or error if no game
        val game = service.findById(gameId)
        if (game == null) {
            throw BadRequestException()
        }
        try {
            service.addPlayer(game, playerName)
        } catch (d: InvalidPlayerException) {
            throw BadRequestException()
        }
        return game
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public class BadRequestException() : RuntimeException() {}

}
