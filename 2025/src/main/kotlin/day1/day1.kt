package uk.co.veryhappythings.day1

import java.io.File
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.round

data class Instruction(val left: Boolean, val distance: Int) {
    fun direction() = if (left) "L" else "R"
}


fun part1() {
    val instructionRegex = Regex("""([LR])(\d+)""")
    var dial = 50
    val stream = File("src/main/resources/day1/input2.txt").inputStream()
    val instructions = mutableListOf<Instruction>()
    stream.bufferedReader().forEachLine {
        val match = instructionRegex.matchEntire(it)
        instructions.add(Instruction(match!!.groups[1]!!.value[0] == 'L', match.groups[2]!!.value.toInt()))
    }

    var totalZeroes = 0
    for (instruction in instructions) {
        if (instruction.left) {
            dial -= instruction.distance
            while (dial < 0) {
                dial = dial + 100
            }
        } else {
            dial += instruction.distance
            while (dial > 99) {
                dial = dial - 100
            }
        }
        //println("${instruction.direction()}${instruction.distance} -> $dial")
        if (dial == 0) {
            totalZeroes += 1
        }
    }
    println(totalZeroes)
}

fun part2() {
    val instructionRegex = Regex("""([LR])(\d+)""")
    val stream = File("src/main/resources/day1/input2.txt").inputStream()
    val instructions = mutableListOf<Instruction>()
    stream.bufferedReader().forEachLine {
        val match = instructionRegex.matchEntire(it)
        instructions.add(Instruction(match!!.groups[1]!!.value[0] == 'L', match.groups[2]!!.value.toInt()))
    }

    var dial = 50
    var totalZeroClicks = 0
    for (instruction in instructions) {
        val oldDial = dial
        if (instruction.left) {
            dial -= instruction.distance
            // I don't like this. I have 2 cases here, one for the case where the dial hasn't spun through a full
            // rotation, and one for when it has.
            // So as it stands, I have to see if it will have clicked through zero at least once, add one if so, and
            // then divide by 100 to get the rest of the clicks. That feels really ugly but I can't see a way around it
            if (oldDial > 0 && dial <= 0) {
                totalZeroClicks++
            }
            totalZeroClicks += abs(dial) / 100
            dial = dial.mod(100)
        } else {
            dial += instruction.distance
            while (dial > 99) {
                dial -= 100
                totalZeroClicks++
            }
        }
        //println("${instruction.direction()}${instruction.distance}: $oldDial -> $dial (${totalZeroClicks})")
    }
    println(totalZeroClicks)
}

fun main() {
    part1()
    part2()
}
