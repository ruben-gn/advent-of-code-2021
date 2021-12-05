package day4

import nl.grootnibbelink.advent2021.util.ResourceLoader

fun main() {
    val input = ResourceLoader.getLines("day4/input.txt")
    val numbers = input[0].split(",")
    val grids = toGrids(input.drop(1))

    val day4 = Day4()

    val part1 = day4.part1(numbers, grids)
    val part2 = day4.part2(numbers, grids)

    println(
        """
        --- Day 4: Giant Squid ---
        Part 1: $part1
        Part 2: $part2
    """.trimIndent()
    )
}

fun <T> List<T>.deconstruct(predicate: (T) -> Boolean): Pair<List<T>, List<T>> =
    Pair(this.takeWhile { predicate(it) }, this.dropWhile { predicate(it) })

fun toGrids(boards: List<String>): List<Board> {
    if (boards.isEmpty()) {
        return emptyList()
    }

    val (board, tails) = boards.dropWhile { it.isEmpty() }.deconstruct { it.isNotEmpty() }

    return listOf(toGrid(board)) + toGrids(tails)
}

fun toGrid(board: List<String>): Board {
    return Board(board.map { toRow(it.dropWhile { it.isWhitespace() }.split("  ", " ")) })
}

fun toRow(row: List<String>): Row {
    return Row(row.map { Cell(it, false) })
}

class Day4 {
    fun part1(numbers: List<String>, boards: List<Board>): Int {
        numbers.forEach { number ->
            boards.forEach { grid ->
                if (grid.mark(number)) {
                    return grid.unmarkedSum() * number.toInt()
                }
            }
        }
        throw IllegalArgumentException("No winning boards found")
    }

    fun part2(numbers: List<String>, boards: List<Board>): Int {
        val winners = mutableSetOf<Board>()
        for (number in numbers) {
            for (grid in boards) {
                if (grid.mark(number)) {
                    winners.add(grid)

                    if (winners.size == boards.size) {
                        return grid.unmarkedSum() * number.toInt()
                    }
                }
            }
        }
        throw IllegalArgumentException("No winning boards found")
    }
}

class Board(private val rows: List<Row>) {
    private fun marked(): Boolean =
        this.rows.any { it.marked() } || anyColumnMarked()

    fun mark(number: String): Boolean {
        rows.forEach { it.mark(number) }
        return marked()
    }

    private fun anyColumnMarked(): Boolean {
        for (i in 0 until rows[0].cells.size) {
            val marked = rows.map { it.cells[i] }.all { it.marked }
            if (marked) return true
        }
        return false
    }

    override fun toString(): String {
        return rows.map { it.toString() }.reduce { acc, row -> acc + "\n" + row }
    }

    fun unmarkedSum(): Int {
        return rows.flatMap { it.cells }
            .filter { !it.marked }
            .map { it.number }
            .map { it.toInt() }
            .reduce { acc, num -> acc + num }
    }
}

class Row(val cells: List<Cell>) {
    fun marked(): Boolean = this.cells.all { it.marked }

    override fun toString(): String {
        return cells.map { it.toString() }.reduce { acc, cell -> acc + cell }
    }

    fun mark(number: String) {
        cells.forEach { it.mark(number) }
    }
}

class Cell(val number: String, var marked: Boolean) {
    override fun toString(): String {
        val m = when (marked) {
            true -> "*"
            false -> " "
        }
        return when (number.length == 1) {
            true -> " $number$m "
            false -> "$number$m "
        }
    }

    fun mark(number: String) {
        if (number == this.number) {
            this.marked = true
        }
    }
}