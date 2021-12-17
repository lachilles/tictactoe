package lachilles.games.tictactoe.impl

data class BoardElement(val row: Int, val col: Int) {
    private var state = 0

    fun setState(x:Int){
        state = x
    }

    fun getValue():Int {
        return state
    }

    fun isEmpty():Boolean {
        return state == 0
    }
}
