package lachilles.games.tictactoe.service

import lachilles.games.tictactoe.impl.Game
import lachilles.games.tictactoe.impl.InvalidPlayerException
import lachilles.games.tictactoe.impl.Move
import lachilles.games.tictactoe.impl.Player
import org.springframework.stereotype.Service

@Service
class TicTacToeService {

    val db = mutableMapOf<String, Game>()

    fun findById(gameId: String) : Game? {
        return db[gameId]
    }

    fun createNewGame(): Game {
        val result = Game()
        db[result.id] = result
        return result
    }

    @Throws(InvalidPlayerException::class)
    fun addPlayer(game: Game, playerName: String) {
        game.addPlayer(Player(playerName))
    }

    @Throws(InvalidPlayerException::class)
    fun takeTurn(game: Game, row: Int, col: Int, playerId: Int) {
        val player = game.getPlayerById(playerId)
        val move = Move(row, col)
        game.takeTurn(player, move)
    }

}
