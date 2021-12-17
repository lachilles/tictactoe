package lachilles.games.tictactoe.impl

class Move(val row: Int, val column: Int) {

    override fun equals(other: Any?): Boolean {
        /// check the type is a Move type
        return other is Move && row == other.row && column == other.column
    }
}
