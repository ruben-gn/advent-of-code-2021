package day9

import nl.grootnibbelink.advent2021.util.ResourceLoader

fun main() {
    val input = ResourceLoader.getLines("day9/input.txt").map { it.split("") }
        .map { line -> line.filter { it.isNotEmpty() }.map { it.toInt() } }

    val day9 = Day9()

    val part1 = day9.part1(input)
    val part2 = day9.part2(input)

    println(
        """
        --- Day 9: Smoke Basin ---
        Part 1: $part1
        Part 2: $part2
    """.trimIndent()
    )
}

class Day9 {
    fun part1(input: List<List<Int>>): Int {
        var risk = 0
        input.forEachIndexed { y, row ->
            row.forEachIndexed inner@{ x, cell ->
                if (y > 0 && input[y - 1][x] <= cell) {
                    return@inner
                }
                if (y < input.size - 1 && input[y + 1][x] <= cell) {
                    return@inner
                }
                if (x > 0 && input[y][x - 1] <= cell) {
                    return@inner
                }
                if (x < row.size - 1 && input[y][x + 1] <= cell) {
                    return@inner
                }
                risk += cell + 1

            }
        }
        return risk
    }

    fun part2(input: List<List<Int>>): Int {
        val visited = MutableList(input.size) { MutableList(input[0].size) { false } }

        val basin = mutableListOf<Int>()
        input.forEachIndexed { y, row ->
            row.forEachIndexed inner@{ x, value ->
                if (visited[y][x] || value == 9) return@inner

                var currentBasin = 0
                val stack = mutableListOf(x to y)

                while (stack.isNotEmpty()) {
                    val (curX, curY) = stack.removeFirst()
                    if (input[curY][curX] == 9) continue

                    visited[curY][curX] = true
                    currentBasin++

                    val neighbours = cardinalDirections(curX, curY)
                        .filter { !stack.contains(it) }
                        .filter { !outOfBounds(visited[0].size - 1, visited.size - 1, it.first, it.second) }
                        .filter { !visited[it.second][it.first] }

                    stack.addAll(neighbours)
                }

                basin.add(currentBasin)
            }
        }

        return basin.sorted().takeLast(3).fold(1) { n, m -> n * m }
    }

    private fun outOfBounds(maxX: Int, maxY: Int, x: Int, y: Int): Boolean {
        return x < 0 || y < 0 || x > maxX || y > maxY
    }

    private fun cardinalDirections(
        curX: Int,
        curY: Int
    ): Collection<Pair<Int, Int>> {
        return listOf(
            curX + 1 to curY,
            curX - 1 to curY,
            curX to curY + 1,
            curX to curY - 1
        )
    }
}