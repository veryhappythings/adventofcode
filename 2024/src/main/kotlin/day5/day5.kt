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

fun fix(rules: List<Rule>, update: List<Int>): List<Int> {
    val original = update.toMutableList()
    val fixed = mutableListOf<Int>()
    while (fixed.size < update.size) {
        val page = original.removeFirst()
        var candidate = 0
        do {
            var allRulesSatisfied = true
            for (rule in rules) {
                if (rule.before == page) {
                    // If our page is in the "before" part of the rule, we need to check every page before the candidate location to see if it's the after part of the rule
                    for (i in 0 until candidate) {
                        if (fixed.size > i && fixed[i] == rule.after) {
                            allRulesSatisfied = false
                        }
                    }
                }
                if (rule.after == page) {
                    // If our page in in the "after" part of the rule, we need to check every page after the candidate location to see if it's the before part of the rule
                    for (i in candidate until fixed.size) {
                        if (fixed[i] == rule.before) {
                            allRulesSatisfied = false
                        }
                    }
                }
                if (!allRulesSatisfied) {
                    break
                }
            }
            if (!allRulesSatisfied) {
                candidate++
            }
        }
        while (!allRulesSatisfied && candidate <= fixed.size)
        fixed.add(candidate, page)
    }
    return fixed
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
            if(validate(rules, update)) {
                validMiddlePages.add(update[(update.size/2)])
            }
        }
    }
    println(validMiddlePages.reduce(Int::plus))
}

fun part2() {
    val stream = File("src/main/resources/day5/input2.txt").inputStream()
    val rules = mutableListOf<Rule>()
    val validMiddlePages = mutableListOf<Int>()
    stream.bufferedReader().forEachLine {
        val split = it.split("|")
        if (split.size == 2) {
            rules.add(Rule(split[0].toInt(), split[1].toInt()))
        } else if (it.isNotEmpty()) {
            val update = it.split(",").map { it.toInt() }
            if(!validate(rules, update)) {
                val fixed = fix(rules, update)
                println("$update -> $fixed")
                validMiddlePages.add(fixed[(fixed.size/2)])
            }
        }
    }
    println(validMiddlePages.reduce(Int::plus))
}

fun main() {
    part1()
    println("--------")
    part2()
}
