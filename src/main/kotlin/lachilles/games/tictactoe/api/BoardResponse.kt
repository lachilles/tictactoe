package lachilles.games.tictactoe.api

import lachilles.games.tictactoe.impl.Board
import lachilles.games.tictactoe.impl.BoardElement
import lachilles.games.tictactoe.impl.Game

data class BoardResponse(val elements: List<BoardElementResponse>) {
    companion object{
        fun fromBoard(game: Game, board: Board?): BoardResponse? {
            if (board == null) {
                return null
            }
            val result = mutableListOf<BoardElementResponse>()
            //create a list of BoardElementResponses
            board.streamElements().forEach {
                    result.add(BoardElementResponse.fromBoardElement(game, it))
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
        fun fromBoardElement(game: Game, element: BoardElement): BoardElementResponse {
            // when element.getValue = 0, return empty string. 1 = 'x' 2 = 'o'
            val display = when (element.getValue()) {
                1 -> "X"
                2 -> "O"
                else -> ""
            }
            // populate the url when display is empty (indicates we can make a move)
            val url = if (display.isNotEmpty() || game.nextPlayer == null) "" else "/takeTurn/${game
            .id}/${element.row}/${element.col}?playerId=${game.nextPlayer?.id}"
            return BoardElementResponse(element.row, element.col, display, url)
        }
    }
}
