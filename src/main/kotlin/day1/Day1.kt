package day1

import nl.grootnibbelink.advent2021.util.ResourceLoader

fun main() {
    val input = ResourceLoader.getLines("day1/input1.txt").map { it.toInt() }

    val part1 = part1(input)
    val part2 = part2(input)

    println("""
        --- Day 1: Sonar Sweep ---
        Part 1: $part1
        Part 2: $part2
    """.trimIndent())
}

fun part1(input: List<Int>): Int {
    return count(input, 1)
}

fun part2(input: List<Int>): Int {
    return count(input, 3)
}

fun count(input: List<Int>, num: Int): Int {
    return input.windowed(num) { it.sum() }
            .zipWithNext()
            .count { it.second > it.first }
}
