package lachilles.games.tictactoe.impl

import java.util.stream.Collectors
import java.util.stream.Stream

//interface ValidMoveProvider {
//    fun getValidMoves(): List<Move>
//}


class Board {
    fun streamElements(): Stream<BoardElement> {
        return elements.stream().flatMap { it.stream() }
    }

    fun takeTurn(player: Player, move: Move) {  //do it command apply function would be Board .taketurn
        // handle conversion of string to int
        // python pseudocode
        // takes a list of the previous state with the memento pattern
        if (elements[move.row][move.column].getValue() != 0) {
            throw IllegalMoveException()
        }

        elements[move.row][move.column].setState(player.id)
    }

    fun getWinningSequences(): List<List<BoardElement>> {
        val result: MutableList<List<BoardElement>> = emptyList<List<BoardElement>>()
                .toMutableList()
        // get the rows
        for (r in 0..2) {
            result.add(elements[r])
        }
        // get the columns
        for (c in 0..2) {
            result.add(elements.stream()
                    .flatMap { m -> m.stream() }
                    .filter{ it.col == c }
                    .collect(Collectors.toList()))
        }
        // get the diagonal
        result.add(elements.stream()
                .flatMap { m -> m.stream() }
                .filter{ it.col == it.row }
                .collect(Collectors.toList()))
        // get the other diagonal
        result.add(elements.stream()
                .flatMap { m -> m.stream() }
                .filter{ it.col == 2 - it.row }
                .collect(Collectors.toList()))
        return result;
    }

    fun anyEmptyElements(): Boolean {
//        // get the rows
//        for (r in 0..2) {
//            // get the columns
//            for (c in 0..2) {
//                if (elements[r][c].getValue() == 0) {
//                    return true
//                }
//            }
//        }
//        return false
        // functional programming approach (same as above, you don't have to keep track of index)
        return streamElements().filter {
            it.isEmpty()
        }.count() > 0
    }

//    internal fun setState(gameState: String) {
//        val it = gameState.splitToSequence(" ", "\n").iterator()
//        for (r in 0..2) {
//            for (c in 0..2) {
//                elements[r][c].setState(toState(it.next()))
//            }
//        }
//    }
//
//    fun empty(move: Move) {
//        elements[move.row][move.column].setState(0)
//    }

//    private fun toState(symbol: String): Int {
//        return when (symbol) {
//            "." -> 0
//            "X" -> 1
//            "O" -> 2
//            else -> throw Exception("unrecognized symbol $symbol")
//        }
//    }

    private val row1 = listOf(
            BoardElement(0, 0),
            BoardElement(0, 1),
            BoardElement(0, 2)
    )
    private val row2 = listOf(
            BoardElement(1, 0),
            BoardElement(1, 1),
            BoardElement(1, 2)
    )
    private val row3 = listOf(
            BoardElement(2, 0),
            BoardElement(2, 1),
            BoardElement(2, 2)
    )
    private val elements = listOf(row1, row2, row3)
}

class IllegalMoveException : RuntimeException() {
}
