package lachilles.games.tictactoe.api

import lachilles.games.tictactoe.impl.Board
import lachilles.games.tictactoe.impl.BoardElement

data class BoardResponse(val elements: List<BoardElementResponse>) {
    companion object{
        fun fromBoard(board: Board): BoardResponse {
            val mlist = mutableListOf<BoardElementResponse>()
            //create a list of BoardElementResponses
            board.streamElements().forEach {
                    mlist.add(BoardElementResponse.fromBoardElement(it))
            }
            // other way of getting elements using map
//            val elements = board.streamElements().map { BoardElementResponse.fromBoardElement(it) }.collect(
//                    Collectors.toList())

            return BoardResponse(mlist)
        }
    }
}

data class BoardElementResponse (val row: Int,
val col: Int, val display: String, val url: String){
    companion object{
        fun fromBoardElement(element: BoardElement): BoardElementResponse {
            // add when element.getvalue = 0, return empty sting. 1 = 'x' 2 = 'o'
            // populate the url when value is a 0 (indicates we can make a move)
            return BoardElementResponse(element.row, element.col, element.getValue().toString(), "")
        }
    }
}
