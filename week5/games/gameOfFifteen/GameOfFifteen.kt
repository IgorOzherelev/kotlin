package games.gameOfFifteen

import week4.board.Cell
import week4.board.Direction
import week4.board.GameBoard
import week4.board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)


class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game{
    private val board: GameBoard<Int?> = createGameBoard<Int?>(4)

    override fun initialize() {
        initializer.initialPermutation.forEachIndexed{ index, value ->
            board[board.getAllCells().elementAt(index)] = value
        }
    }

    override fun canMove(): Boolean = true
    override fun hasWon(): Boolean {
        val cells = board.getAllCells().filter { board[it] != null }
        return cells.zip(cells.drop(1)).all { (first, second) -> board[first]!! < board[second]!! }
    }

    override fun processMove(direction: Direction) {
        with(board) {
            find { it == null }?.also { cell ->
                cell.getNeighbour(direction.reversed())?.also { neighbour ->
                    this[cell] = this[neighbour]
                    this[neighbour] = null
                }
            }

        }
    }

    private fun swap(first: Cell, second: Cell){
        val value = board[first]
        board[first] = board[second]
        board[second] = value
    }

    override fun get(i: Int, j: Int): Int? = board.run {
        this[getCell(i, j)]
    }

}
