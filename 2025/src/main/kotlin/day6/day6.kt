package uk.co.veryhappythings.day6

import java.io.File
import kotlin.math.min

data class Column(val entries: List<Long>, val operator: Char)


fun part1() {
    val stream = File("src/main/resources/day6/input2.txt").inputStream()
    val rows = mutableListOf<MutableList<String>>()
    stream.bufferedReader().forEachLine {
        rows.add(it.trim().split(Regex("""\s+""")).toMutableList())
    }

    val columns = mutableListOf<Column>()
    for (x in rows[0].indices) {
        columns.add(Column(
            rows.slice(0..rows.size-2).map { it[x].toLong() },
            rows[rows.size - 1][x][0]
        ))
    }
    var total: Long = 0
    for (column in columns) {
        val t = when (column.operator) {
            '+' -> column.entries.sum()
            '*' -> column.entries.reduce { acc, i -> acc * i }
            else -> throw Exception("Unknown operator ${column.operator}")
        }
        total += t
    }
    println(total)
}

fun part2() {
    val stream = File("src/main/resources/day6/input2.txt").inputStream()
    val lines = stream.bufferedReader().readLines()
    // Find the matching whitespace column that breaks up the problem
    // Start with the first and last columns in the file
    val breaks = mutableListOf(0, lines.map { it.length }.max())
    for (i in lines[0].indices) {
        var isBreak = true
        for (line in lines) {
            if (line.length > i && line[i] != ' ') {
                isBreak = false
                break
            }
        }
        if (isBreak) {
            breaks.add(i)
        }
    }
    breaks.sort()
    val columns = mutableListOf<Column>()
    for (i in 1..<breaks.size) {
        val start = breaks[i-1]
        val end = breaks[i]
        println(start)
        println(end)
        val entries = mutableListOf<Long>()
        val operator = lines[lines.size-1].substring(start, min(end-1, lines[lines.size-1].length)).trim()[0]
        for (n in start..<end) {
            val value = mutableListOf<Char>()
            for (line in lines.slice(0..lines.size-2)) {
                if (line.length > n) {
                    value.add(line[n])
                }
            }
            val converted = value.joinToString("").trim()
            if (converted != "") {
                entries.add(converted.toLong())
            }

        }
        columns.add(Column(entries, operator))
    }
    var total: Long = 0
    for (column in columns) {
        val t = when (column.operator) {
            '+' -> column.entries.sum()
            '*' -> column.entries.reduce { acc, i -> acc * i }
            else -> throw Exception("Unknown operator ${column.operator}")
        }
        total += t
    }
    println(total)
}

fun main() {
    part1()
    part2()
}
