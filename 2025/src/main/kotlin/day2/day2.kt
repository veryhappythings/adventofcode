package uk.co.veryhappythings.day2

import java.io.File
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.round
import kotlin.random.Random

fun part1() {
    val rangeRegex = Regex("""(\d+)-(\d+)""")
    val stream = File("src/main/resources/day2/input2.txt").inputStream()
    val reader = stream.bufferedReader()
    val line = reader.readLine()
    var invalidIds = mutableListOf<Long>()
    line.split(",").map {
        val match = rangeRegex.matchEntire(it)
        val first = match!!.groups[1]!!.value.toLong()
        val second = match!!.groups[2]!!.value.toLong()
        println("Range: $first to $second")
        for (i in first..second) {
            val productId = i.toString()
            if (productId.length % 2 != 0) {
                continue
            }
            if (productId.substring(0, productId.length / 2) == productId.substring(productId.length / 2)) {
                println("Found invalid product ID: $productId")
                invalidIds.add(productId.toLong())
            }
        }
    }
    println(invalidIds.sum())
}

fun part2() {
    val rangeRegex = Regex("""(\d+)-(\d+)""")
    val stream = File("src/main/resources/day2/input2.txt").inputStream()
    val reader = stream.bufferedReader()
    val line = reader.readLine()
    var invalidIds = mutableListOf<Long>()
    line.split(",").map {
        val match = rangeRegex.matchEntire(it)
        val first = match!!.groups[1]!!.value.toLong()
        val second = match!!.groups[2]!!.value.toLong()
        println("Range: $first to $second")
        for (i in first..second) {
            val productId = i.toString()
            for (possibleMatchLength in (productId.length / 2) downTo 1) {
                var valid = true
                val possibleMatch = productId.substring(0, possibleMatchLength)
                var buffer = productId
                println("Checking product ID $productId for possible match $possibleMatch")
                while (buffer.length >= possibleMatchLength) {
                    if (buffer.substring(0, possibleMatchLength) == possibleMatch) {
                        buffer = buffer.substring(possibleMatchLength)
                        // If the buffer is empty, we found a repeating match that went all the way to the end so it's invalid
                        if (buffer.isEmpty()) {
                            valid = false
                        }
                    } else {
                        valid = true
                        break
                    }
                }
                if (!valid) {
                    println("Found invalid product ID: $productId")
                    invalidIds.add(productId.toLong())
                    break
                }
            }
        }
    }
    println(invalidIds.sum())
}

fun main() {
    //part1()
    part2()
}
