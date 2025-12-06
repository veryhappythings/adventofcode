package uk.co.veryhappythings.day3

import uk.co.veryhappythings.day1.Instruction
import java.io.File

fun part1() {
    val stream = File("src/main/resources/day3/input2.txt").inputStream()
    val banks = mutableListOf<String>()
    var totalJoltage = 0
    stream.bufferedReader().forEachLine {
        banks.add(it)
    }
    for (bank in banks) {
        var highestIndex = 0
        var highestValue = 0
        for ((i, c) in bank.withIndex()) {
            // Don't bother with the last number, because we need at least 2 digits
            if (i == bank.length - 1) {
                continue
            }
            if (c.toString().toInt() > highestValue) {
                highestIndex = i
                highestValue = c.toString().toInt()
            }
        }

        var nextHighest = 0
        for (c in bank.substring(highestIndex + 1, bank.length)) {
            if (c.toString().toInt() > nextHighest) {
                nextHighest = c.toString().toInt()
            }
        }

        val joltage = (highestValue.toString() + nextHighest.toString()).toInt()
        totalJoltage += joltage
    }
    println(totalJoltage)
}

fun part2() {
    val stream = File("src/main/resources/day3/input2.txt").inputStream()
    val banks = mutableListOf<String>()
    var  totalJoltage = 0.toLong()
    stream.bufferedReader().forEachLine {
        banks.add(it)
    }
    for (bank in banks) {
        println("Processing bank: $bank")
        var desiredBatteries = 12
        var batteries = mutableListOf<Int>()
        var highestIndex = -1
        var oldHighestIndex = 0
        while (batteries.size < desiredBatteries) {
            oldHighestIndex = highestIndex
            var highestValue = 0
            // len 10, need to consider 0-9
            // pick pos 0, have 1 battery, need to consider 1-10 -
            // highestIndex + 1 to len - (desiredBatteries - batteries.size -1)
            println(bank)
            for (i in 0..highestIndex) {
                print(" ")
            }
            println(bank.substring(highestIndex + 1, bank.length - (desiredBatteries - batteries.size - 1)))
            for ((i, c) in bank.substring(highestIndex + 1, bank.length - (desiredBatteries - batteries.size - 1)).withIndex()) {
                if (c.toString().toInt() > highestValue) {
                    highestIndex = i + oldHighestIndex + 1
                    highestValue = c.toString().toInt()
                }
            }
            batteries.add(highestValue)
        }
        val joltage = batteries.joinToString("") { it.toString() }.toLong()
        totalJoltage += joltage
    }
    println(totalJoltage)

}

fun main() {
    part1()
    part2()
}
