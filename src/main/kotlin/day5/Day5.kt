package day5

import nl.grootnibbelink.advent2021.util.ResourceLoader

fun main() {
    val input = ResourceLoader.getLines("day5/input.txt")
        .map { it.split(" -> ") }
        .map { Wall(start = toPosition(it[0]), end = toPosition(it[1])) }

    val day5 = Day5()

    val part1 = day5.part1(input)
    val part2 = day5.part2(input)

    println(
        """
        --- Day 5: Hydrothermal Venture ---
        Part 1: $part1
        Part 2: $part2
    """.trimIndent()
    )
}

fun toPosition(coord: String): Position {
    val (x, y) = coord.split(",")
    return Position(x.toInt(), y.toInt())
}

class Day5 {
    fun part1(input: List<Wall>): Int {
        val map: MutableList<MutableList<Int>> = createStartingMap(input)

        input.forEach {
            if (it.start.x != it.end.x && it.start.y != it.end.y) return@forEach
            drawWallOnMap(it, map)
        }
        return map.map { a -> a.count { it > 1 } }.reduce { acc, num -> acc + num }
    }

    fun part2(input: List<Wall>): Int {
        val map: MutableList<MutableList<Int>> = createStartingMap(input)

        input.forEach { drawWallOnMap(it, map) }

        return map.map { a -> a.count { it > 1 } }.reduce { acc, num -> acc + num }
    }

    private fun createStartingMap(input: List<Wall>): MutableList<MutableList<Int>> {
        val maxX = input.maxOf { if (it.start.x > it.end.x) it.start.x else it.end.x }
        val maxY = input.maxOf { if (it.start.y > it.end.y) it.start.y else it.end.y }

        return List(maxY + 1) { List(maxX + 1) { 0 }.toMutableList() }.toMutableList()
    }

    private fun drawWallOnMap(
        wall: Wall,
        map: MutableList<MutableList<Int>>
    ) {
        val current = Position(wall.start.x, wall.start.y)
        while (current.y != wall.end.y || current.x != wall.end.x) {
            map[current.y][current.x] += 1
            current.x -= current.x.compareTo(wall.end.x)
            current.y -= current.y.compareTo(wall.end.y)
        }
        map[current.y][current.x] += 1
    }
}

data class Wall(val start: Position, val end: Position)
data class Position(var x: Int, var y: Int)