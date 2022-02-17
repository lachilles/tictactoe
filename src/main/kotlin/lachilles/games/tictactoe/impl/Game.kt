package lachilles.games.tictactoe.impl

import java.util.*

class Game(var id: String = UUID.randomUUID().toString()) {

    val players: MutableList<Player> = mutableListOf()
    var board: Board? = null
    var nextPlayer: Player? = null
    var winner: Player? = null

    fun addPlayer(p: Player) {
        val existing = players.stream().map { it.name }.anyMatch { it == p.name }
        if (existing) throw InvalidPlayerException()
        if (players.size >= 2) throw InvalidPlayerException()
        players.add(p)
        if (players.size == 1) {
            p.symbol = 'X'
            p.id = 1
            nextPlayer = p
        } else {
            p.symbol = 'O'
            p.id = 2
        }
        if (players.size == 2) {
            board = Board()
        }
    }

    fun takeTurn(player: Player, move: Move) {
        if (winner != null) {
            throw EndOfGameException()
        }

        if (player != nextPlayer) {
            throw InvalidPlayerException()
        }
        // let = if board isn't null
        winner = board?.let {
            it.takeTurn(player, move)
            WinnerDetector().detectWinner(it, players[0], players[1])
        }
        if (winner == null) {
            nextPlayer = swapPlayers()
        }

        // todo: if we have a winner then set nextPLayer to null and
        // todo: set nextPlayer to be null when you reach 9 moves
    }

    private fun swapPlayers(): Player {
        return if (nextPlayer == players[0]) {
            players[1]
        } else {
            players[0]
        }
    }

    fun getPlayerById(playerId: Int): Player {
        return players[playerId - 1]
    }
}

class EndOfGameException : Exception() {

}

class InvalidPlayerException : Exception()
