package day8

import nl.grootnibbelink.advent2021.util.ResourceLoader

fun main() {
    val input = ResourceLoader.getLines("day8/input.txt").map { Pair(values(it, 0), values(it, 1)) }

    val day8 = Day8()

    val part1 = day8.part1(input)
    val part2 = day8.part2(input)

    println(
        """
        --- Day 8: Seven Segment Search ---
        Part 1: $part1
        Part 2: $part2
    """.trimIndent()
    )
}

fun values(list: String, pos: Int): List<String> {
    return list.split("|")[pos].split(" ")
}

class Day8 {
    fun part1(input: List<Pair<List<String>, List<String>>>): Int {
        return input.map { it.second }.sumOf { it.count { num -> num.length in listOf(2, 3, 4, 7) } }
    }

    fun part2(input: List<Pair<List<String>, List<String>>>): Int {
        return input.sumOf { line -> fromLine(line.first.map { it.toSet() }, line.second.map { it.toSet() }) }
    }

    private fun fromLine(input: List<Set<Char>>, output: List<Set<Char>>): Int {
        val one = input.first { it.size == 2 }
        val four = input.first { it.size == 4 }
        val seven = input.first { it.size == 3 }
        val eight = input.first { it.size == 7 }

        val three = input.filter { it.containsAll(seven) }.first { it.subtract(seven).size == 2 }

        val nine = input.filter { it.containsAll(three) }.first { it.subtract(three).size == 1 }
        val six = input.filterNot { it.containsAll(seven) }.first { it.size == 6 }
        val zero = input.filter { it.size == 6 }.first { it != nine && it != six }

        val five = input.filter { six.containsAll(it) }.first { six.subtract(it).size == 1 }
        val two = input.filter { it.size == 5 }.first { it != three && it != five }

        return output.map {
            when (it) {
                zero -> "0"
                one -> "1"
                two -> "2"
                three -> "3"
                four -> "4"
                five -> "5"
                six -> "6"
                seven -> "7"
                eight -> "8"
                nine -> "9"
                else -> "0"
            }
        }.reduce { acc, num -> acc + num }.toInt()
    }
}