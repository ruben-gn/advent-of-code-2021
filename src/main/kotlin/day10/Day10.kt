package day10

import nl.grootnibbelink.advent2021.util.ResourceLoader

fun main() {
    val input = ResourceLoader.getLines("day10/input.txt").map { it.split("") }
        .filter { it.isNotEmpty() }

    val day10 = Day10()

    val part1 = day10.part1(input)
    val part2 = day10.part2(input)

    println(
        """
        --- Day 10: Syntax Scoring ---
        Part 1: $part1
        Part 2: $part2
    """.trimIndent()
    )
}

class Day10 {
    private val pairs = listOf(
        P("(", ")", 3, 1),
        P("[", "]", 57, 2),
        P("{", "}", 1197, 3),
        P("<", ">", 25137, 4)
    )

    fun part1(input: List<List<String>>): Int {
        var runningTotal = 0
        input.forEach outer@{ line ->
            val stack = ArrayDeque<String>()
            line.filter { it.isNotEmpty() }.forEach { symbol ->
                if (isOpening(symbol)) {
                    stack.addLast(symbol)
                } else {
                    val closingPair = fromClosingSymbol(symbol)
                    if (closingPair.l != stack.removeLast()) {
                        runningTotal += closingPair.v1
                        return@outer
                    }
                }
            }
        }
        return runningTotal
    }

    private fun fromClosingSymbol(symbol: String): P {
        return pairs.first { it.r == symbol }
    }

    private fun isOpening(symbol: String): Boolean {
        return pairs.map { it.l }.any { it == symbol }
    }

    fun part2(input: List<List<String>>): Int {
        return 2
    }

    class P(val l: String, val r: String, val v1: Int, val v2: Int)
}