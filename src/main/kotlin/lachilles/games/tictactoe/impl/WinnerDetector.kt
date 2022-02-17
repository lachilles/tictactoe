package lachilles.games.tictactoe.impl

import java.util.stream.Collectors

class WinnerDetector {

    fun detectWinner(board: Board, player1 : Player, player2 : Player) : Player? {
        val winningId = detectWinner(board)
        if (winningId == 0) {
            return null
        }
        if (player1.id == winningId) {
            return player1
        } else if (player2.id == winningId) {
            return player2
        } else {
            throw Exception("befuddled")
        }
    }

    private fun detectWinner(board: Board) : Int {
        for(sequence in board.getWinningSequences()) {
            val sum = sequence.stream().collect(Collectors.summingInt { it.getValue() })
            // this could break if there's a player id 3
            val nonEmpty = sequence.stream().filter { !it.isEmpty() }.count().toInt()
            if (nonEmpty == 3 && sum == 3 || sum == 6) {
                return sum / 3
            }
        }
        return 0
    }
}

