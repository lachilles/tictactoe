package lachilles.games.tictactoe.api

import lachilles.games.tictactoe.impl.Player
import java.util.stream.Collectors

data class PlayerResponse(val name: String, val id: Int) {

    companion object {
        fun fromPlayers(players: List<Player>) : List<PlayerResponse> {
            return players.stream().map { PlayerResponse(it.name, it.id) }.collect(Collectors.toList())

            // alternate old school/java implementation
//            val result:MutableList<PlayerResponse> = mutableListOf()
//            for( c in players) {
//                result.add(PlayerResponse(c.name, c.id))
//            }
//            return result
        }
    }
}
