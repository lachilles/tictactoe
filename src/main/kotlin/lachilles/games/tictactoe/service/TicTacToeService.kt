package lachilles.games.tictactoe.service

import lachilles.games.tictactoe.GameResponse
import lachilles.games.tictactoe.PlayerResponse
import org.springframework.stereotype.Service

@Service
class TicTacToeService {

    val db = mutableMapOf<String, GameResponse>()

    fun findById(gameId: String) : GameResponse? {
        return db[gameId]
    }

    fun createNewGame(): GameResponse {
        val result = GameResponse()
        db[result.id] = result
        return result
    }

    @Throws(InvalidPlayerException::class)
    fun addPlayer(game: GameResponse, playerName: String) {
        val existing = game.players.stream().map { p -> p.name }.anyMatch { it == playerName }
        if ( existing ) throw InvalidPlayerException()
        if (game.players.size >= 2) throw InvalidPlayerException()
        game.players.add(PlayerResponse(playerName))
    }

}

class InvalidPlayerException() : Exception()
