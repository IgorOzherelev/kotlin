package board

import week4.board.Cell
import week4.board.Direction
import week4.board.Direction.*
import week4.board.GameBoard
import week4.board.GameBoardImpl
import week4.board.SquareBoard
import week4.board.SquareBoardImpl

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)


class GameBoardImpl<T>(width: Int) : SquareBoardImpl(width), GameBoard<T> {
    private val data: MutableMap<Cell, T?> = hashMapOf()

    init {
        getAllCells().forEach { set(it, null)}
    }

    override fun get(cell: Cell): T? = data[cell]

    override fun set(cell: Cell, value: T?) {
        data[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> =
            data.filter { predicate(it.value) }.keys


    override fun find(predicate: (T?) -> Boolean): Cell? =
            filter(predicate).firstOrNull()

    override fun any(predicate: (T?) -> Boolean): Boolean =
            !filter(predicate).isEmpty()

    override fun all(predicate: (T?) -> Boolean): Boolean =
            filter(predicate).size == data.size
}


open class SquareBoardImpl(override val width: Int) : SquareBoard {
    private val cells = (1..width).flatMap { i -> (1..width).map{ Cell(i, it) } }

    override fun getCellOrNull(i: Int, j: Int): Cell? =
            cells.firstOrNull { it.i == i && it.j == j }


    override fun getCell(i: Int, j: Int): Cell = getCellOrNull(i, j)!!

    override fun getAllCells(): Collection<Cell> = cells

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val row = cells.filter{ it.i == i }

        return jRange.takeWhile { it <= width }.map { row.find { cell -> cell.j == it } as Cell }
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val column = cells.filter{ it.j == j }

        return iRange.takeWhile { it <= width }.map{ column.find{ cell -> cell.i == it } as Cell }
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? =
            when (direction){
                DOWN -> getCellOrNull(i + 1, j)
                UP -> getCellOrNull(i - 1, j)
                LEFT -> getCellOrNull(i, j - 1)
                RIGHT -> getCellOrNull(i, j + 1)
            }

}