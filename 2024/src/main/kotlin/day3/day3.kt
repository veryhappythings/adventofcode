package uk.co.veryhappythings.day3

import java.io.File

fun part1() {
    val stream = File("src/main/resources/day3/input2.txt").inputStream()
    val memory = stream.bufferedReader().readText()
    val command = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
    var total = 0
    val matchResult = command.findAll(memory)
    println(matchResult.count())
    for (match in matchResult) {
        println(match.value)
        total += match.groups[1]?.value!!.toInt() * match.groups[2]?.value!!.toInt()
    }
    println(total)
}

fun part2() {
    val stream = File("src/main/resources/day3/input2.txt").inputStream()
    var memory = stream.bufferedReader().readText()
    val mulRegex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
    val doRegex = Regex("""do\(\)""")
    val dontRegex = Regex("""don't\(\)""")
    var enabled = true
    var total = 0
    do {
        val mulMatch = mulRegex.find(memory)
        val doMatch = doRegex.find(memory)
        val dontMatch = dontRegex.find(memory)
        val lowest = listOfNotNull(mulMatch, doMatch, dontMatch).minByOrNull { it.range.first }
        if (doMatch != null && doMatch == lowest) {
            enabled = true
            memory = memory.substring(doMatch.range.last + 1)
        }
        if (dontMatch != null && dontMatch == lowest) {
            enabled = false
            memory = memory.substring(dontMatch.range.last + 1)
        }
        if (mulMatch != null && mulMatch == lowest) {
            if (enabled) {
                total += mulMatch.groups[1]?.value!!.toInt() * mulMatch.groups[2]?.value!!.toInt()
            }
            memory = memory.substring(mulMatch.range.last + 1)
        }

    } while (mulMatch != null)
    println(total)
}

fun main() {
    part1()
    println("--------")
    part2()
}