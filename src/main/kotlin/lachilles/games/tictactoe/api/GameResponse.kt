package lachilles.games.tictactoe.api

import lachilles.games.tictactoe.impl.Game
import lachilles.games.tictactoe.impl.GameState

data class GameResponse(var id: String) {

    var players = listOf<PlayerResponse>()
    var board: BoardResponse? = null
    var winner: PlayerResponse? = null
    var status: GameState? = null

    companion object {
        fun fromGame(g: Game): GameResponse {
            val result = GameResponse(id = g.id)
            result.status = g.gameState
            result.players = PlayerResponse.fromPlayers(g.players)
            result.board = BoardResponse.fromBoard(g, g.board)
            g.winner?.let {
                result.winner = PlayerResponse.fromPlayer(it)
            }
            return result
        }
    }

}
