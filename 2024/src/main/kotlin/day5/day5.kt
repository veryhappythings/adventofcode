package uk.co.veryhappythings.day5

import java.io.File

data class Rule(val before: Int, val after: Int)

fun validate(rules: List<Rule>, update: List<Int>): Boolean {
    for (i in update.indices) {
        val page = update[i]
        for (rule in rules) {
            if (rule.before == page) {
                if (update.indexOf(rule.after) != -1 && update.indexOf(rule.after) < i) {
                    return false
                }
            }
        }
    }
    return true
}

fun part1() {
    val stream = File("src/main/resources/day5/input2.txt").inputStream()
    val rules = mutableListOf<Rule>()
    val validMiddlePages = mutableListOf<Int>()
    stream.bufferedReader().forEachLine {
        val split = it.split("|")
        if (split.size == 2) {
            rules.add(Rule(split[0].toInt(), split[1].toInt()))
        } else if (it.length > 0) {
            val update = it.split(",").map { it.toInt() }
            println(update)
            println(validate(rules, update))
            if(validate(rules, update)) {
                validMiddlePages.add(update[(update.size/2)])
            }
        }
    }
    println(validMiddlePages.reduce(Int::plus))
}

fun part2() {
}

fun main() {
    part1()
    println("--------")
    part2()
}
