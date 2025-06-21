package uk.co.veryhappythings.day7

import java.io.File

// Given a list of char options, picks all combinations of length `size`
fun findCombinations(options: List<Char>, size: Int): MutableList<MutableList<Char>> {
    return findCombinations(options, size, mutableListOf())
}

fun findCombinations(options: List<Char>, size: Int, prefix: MutableList<Char>): MutableList<MutableList<Char>> {
    val result = mutableListOf<MutableList<Char>>()
    // If we're looking for nothing more, return what we've got
    if (size == 0) {
        return mutableListOf(prefix)
    }
    // Otherwise recurse for each option
    for (option in options) {
        result += findCombinations(options, size - 1, (prefix + mutableListOf(option)).toMutableList())
    }
    return result
}

fun part1() {
    val possibleOperators = listOf('+', '*')
    val stream = File("src/main/resources/day7/input2.txt").inputStream()
    var calibrationResult = (0).toBigInteger()
    stream.bufferedReader().forEachLine {
        val split = it.split(':')
        val total = split[0].toBigInteger()
        val values = split[1].trim().split(' ').map { it.toBigInteger() }
        val numberOfOperators = values.size - 1
        println("$total: $values")
        val combinations = findCombinations(possibleOperators, numberOfOperators)
        for (combination in combinations) {
            var result = values[0]
            for (i in combination.indices) {
                val operator = combination[i]
                when (operator) {
                    '+' -> result += values[i + 1]
                    '*' -> result *= values[i + 1]
                }
            }
            println(result)
            if (result == total) {
                calibrationResult += result
                break
            }
        }
    }
    println("Calibration Result: $calibrationResult")
}

fun part2() {

}

fun main() {
    part1()
    println("------")
    part2()
}