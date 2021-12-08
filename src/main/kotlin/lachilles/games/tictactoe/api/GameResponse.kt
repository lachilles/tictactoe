package lachilles.games.tictactoe.api

import java.util.*

data class GameResponse(var id: String = UUID.randomUUID().toString()) {

    val players = mutableListOf<PlayerResponse>()
    var board: BoardResponse? = null

}
