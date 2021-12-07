package day7

import nl.grootnibbelink.advent2021.util.ResourceLoader
import kotlin.math.abs
import kotlin.math.floor

fun main() {
    val input = ResourceLoader.getLines("day7/input.txt").single().split(",").map { it.toInt() }

    val day7 = Day7()

    val part1 = day7.part1(input)
    val part2 = day7.part2(input)

    println(
        """
        --- Day 7: The Treachery of Whales ---
        Part 1: $part1
        Part 2: $part2
        
        median: ${day7.median(input)}
        mean:   ${day7.mean(input)}
    """.trimIndent()
    )
}

class Day7 {

    /**
     * Naive implementations
     */
    fun part1(input: List<Int>): Int {
        return rangeMinMax(input).map { getFuel(input, it) }.minOf { it }
    }

    fun part2(input: List<Int>): Int {
        return rangeMinMax(input).map { getFuelIncrementing(input, it) }.minOf { it }
    }

    private fun rangeMinMax(input: List<Int>) = (input.minOf { it }..input.maxOf { it })

    private fun getFuel(input: List<Int>, destination: Int) =
        input.sumOf { distance(it, destination) }

    private fun getFuelIncrementing(input: List<Int>, target: Int) =
        input.map { distance(it, target) }.sumOf { it * (it + 1) / 2 }

    private fun distance(start: Int, end: Int) = abs(start - end)

    /**
     * Implementations that were thought about a bit more
     */
    fun median(input: List<Int>): Int {
        val median = input.sorted()[input.size / 2]
        return getFuel(input, median)
    }

    fun mean(input: List<Int>): Int {
        val meanLow = floor(input.average()).toInt()
        val meanHigh = meanLow + 1

        return minOf(getFuelIncrementing(input, meanLow), getFuelIncrementing(input, meanHigh))
    }
}