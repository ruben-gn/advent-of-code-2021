package day6

import nl.grootnibbelink.advent2021.util.ResourceLoader
import kotlin.system.measureNanoTime

fun main() {
    val input = ResourceLoader.getLines("day6/input.txt").single().split(",").map { it.toInt() }
    val day6 = Day6()

    val part1 = day6.part1(input)
    val (part2, duration) = day6.part2(input)

    println(
        """
        --- Day 6: Lanternfish ---
        Part 1: $part1
        Part 2: $part2 ($duration nanoseconds)
    """.trimIndent()
    )
}

class Day6 {
    fun part1(input: List<Int>): Int {
        return procreate(input, 80).size
    }

    fun part2(input: List<Int>): Pair<Long, Long> {
        val lanternfish = LongArray(9) { 0 }

        val nanos = measureNanoTime {
            for (fish in input) lanternfish[fish]++

            repeat(256) {
                val fishAtZero = lanternfish[0]
                lanternfish.copyInto(lanternfish, 0, 1)
                lanternfish[6] += fishAtZero
                lanternfish[8] = fishAtZero
            }
        }

        return Pair(lanternfish.sum(), nanos)
    }

    private fun procreate(input: List<Int>, days: Int): List<Int> {
        return if (days == 0) input
        else procreate(input.flatMap {
            when (it) {
                0 -> listOf(6, 8)
                else -> listOf(it - 1)
            }
        }, days - 1)
    }
}