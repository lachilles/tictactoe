package lachilles.games.tictactoe.api

import lachilles.games.tictactoe.impl.Board
import lachilles.games.tictactoe.impl.BoardElement

data class BoardResponse(val elements: List<BoardElementResponse>) {
    companion object{
        fun fromBoard(board: Board): BoardResponse {
            val elements = listOf(BoardElementResponse.fromBoardElement(board.elements[0][0]))
            // have a list of BoardElementResponses
            val result = BoardResponse(elements)
            return result
        }
    }
}

data class BoardElementResponse (val row: Int,
val col: Int, val display: String, val url: String){
    companion object{
        fun fromBoardElement(element: BoardElement): BoardElementResponse {
            return BoardElementResponse(element.row, element.col, element.getValue().toString(), "")
        }
    }
}
