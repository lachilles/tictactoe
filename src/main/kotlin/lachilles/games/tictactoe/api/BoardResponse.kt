package lachilles.games.tictactoe.api

import lachilles.games.tictactoe.impl.Board
import lachilles.games.tictactoe.impl.BoardElement

data class BoardResponse(val elements: List<BoardElementResponse>) {
    companion object{
        fun fromBoard(board: Board?): BoardResponse? {
            if (board == null) {
                return null
            }
            val result = mutableListOf<BoardElementResponse>()
            //create a list of BoardElementResponses
            board.streamElements().forEach {
                    result.add(BoardElementResponse.fromBoardElement(it))
            }
            // other way of getting elements using map
//            val elements = board.streamElements().map { BoardElementResponse.fromBoardElement(it) }.collect(
//                    Collectors.toList())

            return BoardResponse(result)
        }
    }
}

data class BoardElementResponse (val row: Int,
                                 val col: Int,
                                 val display: String,
                                 val url: String){
    companion object{
        fun fromBoardElement(element: BoardElement): BoardElementResponse {
            // when element.getvalue = 0, return empty string. 1 = 'x' 2 = 'o'
            val display = when (element.getValue()) {
                1 -> "X"
                2 -> "O"
                else -> ""
            }
            // populate the url when display is empty (indicates we can make a move)
            val url = if (!display.isEmpty()) "" else "http://foo"
            return BoardElementResponse(element.row, element.col, display, url)
        }
    }
}
