package lachilles.games.tictactoe.api

import lachilles.games.tictactoe.impl.EndOfGameException
import lachilles.games.tictactoe.impl.IllegalMoveException
import lachilles.games.tictactoe.impl.InvalidPlayerException
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
        return GameResponse.fromGame(service.createNewGame())
    }

    @PostMapping("/createPlayer/{gameId}")
    fun createPlayer(@PathVariable("gameId") gameId: String, @RequestParam playerName: String): GameResponse {
        // business logic:
        //   look up game by id
        //   add player to the game
        //   return result, or error if no game
        val game = service.findById(gameId)
        if (game == null) {
            throw BadRequestException(message="No such game")
        }
        try {
            service.addPlayer(game, playerName)
        } catch (d: InvalidPlayerException) {
            throw BadRequestException(message="Cannot add another player")
        }
        return GameResponse.fromGame(game)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    class BadRequestException(message: String) : RuntimeException(message)

    @GetMapping("/getGameState/{gameId}")
    fun getGameState(@PathVariable("gameId") gameId: String): GameResponse
    {
        val game = service.findById(gameId)
        if (game == null) {
            throw BadRequestException(message="No such game")
        }
        return GameResponse.fromGame(game)
    }

    @PutMapping("takeTurn/{gameId}/{row}/{col}")
    fun takeTurn(@PathVariable("gameId") gameId: String,
                 @PathVariable("row") row: Int,
                 @PathVariable("col") col: Int,
                 @RequestParam playerId: Int): GameResponse
    {
        val game = service.findById(gameId)
        if (game == null) {
            throw BadRequestException(message="No such game")
        }

        try {
            service.takeTurn(game, row, col, playerId)
        } catch (d: InvalidPlayerException) {
            throw BadRequestException(message="Not your turn")
        } catch (e: IllegalMoveException) {
            throw BadRequestException(message="That space is already taken")
        } catch (f: EndOfGameException) {
            throw BadRequestException(message="The game has ended already")
        }
        return GameResponse.fromGame(game)
    }

}
