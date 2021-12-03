package day2

import nl.grootnibbelink.advent2021.util.ResourceLoader

fun main() {
    val input = ResourceLoader.getLines("day2/input.txt").map { toPair(it) }

    val part1 = part1(input)
    val part2 = part2(input)

    println("""
        --- Day 2: Dive! ---
        Part 1: $part1
        Part 2: $part2
    """.trimIndent())
}

private fun part1(input: List<Pair<String, Int>>): Int {
    val (horizontal, depth) = input.fold(Position(0, 0, 0)) { acc, (direction, amount) ->
        when (direction) {
            "forward" -> acc.copy(horizontal = acc.horizontal + amount)
            "down" -> acc.copy(depth = acc.depth + amount)
            "up" -> acc.copy(depth = acc.depth - amount)
            else -> throw IllegalArgumentException("Unknown direction: $direction")
        }
    }
    return horizontal * depth
}

private fun part2(input: List<Pair<String, Int>>): Int {
    val (horizontal, depth) = input.fold(Position(0, 0, 0)) { acc, (direction, amount) ->
        when (direction) {
            "forward" -> acc.copy(
                    horizontal = acc.horizontal + amount,
                    depth = acc.depth + amount * acc.aim
            )
            "down" -> acc.copy(aim = acc.aim + amount)
            "up" -> acc.copy(aim = acc.aim - amount)
            else -> throw IllegalArgumentException("Unknown direction: $direction")
        }
    }
    return horizontal * depth
}

private fun toPair(it: String): Pair<String, Int> {
    val split = it.split(" ")
    return Pair(split[0], split[1].toInt())
}

data class Position(val horizontal: Int, val depth: Int, val aim: Int)
