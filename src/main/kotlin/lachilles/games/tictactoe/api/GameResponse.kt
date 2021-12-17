package lachilles.games.tictactoe.api

import lachilles.games.tictactoe.impl.Game

data class GameResponse(var id: String) {

    var players = listOf<PlayerResponse>()
    var board: BoardResponse? = null

    companion object {
        fun fromGame(g: Game): GameResponse {
            val result = GameResponse(id = g.id)
            result.players = PlayerResponse.fromPlayers(g.players)
            result.board = BoardResponse.fromBoard(g.board)
            return result
        }
    }

}
