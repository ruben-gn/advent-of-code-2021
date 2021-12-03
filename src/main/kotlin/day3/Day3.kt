package day3

import nl.grootnibbelink.advent2021.util.ResourceLoader
import kotlin.math.roundToInt

fun main() {
    val input = ResourceLoader.getLines("day3/input.txt")
            .map { it.toList() }
            .map { lists ->
                lists.map {
                    it.digitToInt()
                }
            }
    val day3 = Day3()

    val part1 = day3.part1(input)
    val part2 = day3.part2(input)

    println("""
        --- Day 3: Binary Diagnostic ---
        Part 1: $part1
        Part 2: $part2
    """.trimIndent())
}

class Day3 {
    fun part1(input: List<List<Int>>): Int {
        val gamma = getMostUsedBits(input)
        val epsilon = bitFlip(gamma)

        return toInt(gamma) * toInt(epsilon)
    }

    private fun getMostUsedBits(input: List<List<Int>>) =
            input.reduce { acc, list -> addLists(acc, list) }
                    .map { it.toDouble() / input.size }
                    .map { it.roundToInt() }

    private fun addLists(acc: List<Int>, list: List<Int>) = acc.zip(list).map { (a, b) -> a + b }

    private fun toInt(intList: List<Int>): Int = Integer.parseInt(intList.joinToString(separator = ""), 2)

    private fun bitFlip(bits: List<Int>): List<Int> = bits.map { (it - 1) * -1 }


    fun part2(input: List<List<Int>>): Int {
        val oxygen = findRating(input.toMutableList(), this::findMostCommonAt)
        val co2 = findRating(input, this::findLeastCommonAt)

        return oxygen * co2
    }

    private fun findRating(input: List<List<Int>>, findSignificantBit: (List<List<Int>>, Int) -> Int): Int {
        var mutableInput: List<List<Int>> = input.toMutableList()
        var counter = 0

        while (mutableInput.size > 1) {
            val significantBit: Int = findSignificantBit(mutableInput, counter)

            mutableInput = mutableInput.filter { it[counter] == significantBit }.distinct()
            counter += 1
        }

        return toInt(mutableInput[0])
    }

    private fun findMostCommonAt(input: List<List<Int>>, counter: Int) =
            getMostUsedBits(input)[counter]

    private fun findLeastCommonAt(input: List<List<Int>>, counter: Int) =
            bitFlip(getMostUsedBits(input))[counter]
}