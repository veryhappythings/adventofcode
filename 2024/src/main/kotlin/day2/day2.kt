package uk.co.veryhappythings.day2

import java.io.File

fun isReportSafe(report: List<Int>): Boolean {
    var ok = true
    for (i in report.indices) {
        if (i == report.size - 1) {
            break
        }
        // If you sort it ascending and it doesnt change or you sort it descending and it doesn't change, it's ok
        if (report.sorted() != report && report.sortedDescending() != report) {
            ok = false
            break
        }
        // the difference must always be above zero and below 4
        val difference = Math.abs(report[i] - report[i + 1])
        if (difference !in 1..3) {
            ok = false
            break
        }
    }
    return ok
}

fun part1() {
    val stream = File("src/main/resources/day2/input2.txt").inputStream()
    var safe = 0
    stream.bufferedReader().forEachLine {
        val report = it.split(Regex("\\s+")).map { it.toInt() }
        if (isReportSafe(report)) {
            safe++
        }
    }
    println(safe)
}

fun part2() {
    val stream = File("src/main/resources/day2/input2.txt").inputStream()
    var safe = 0
    stream.bufferedReader().forEachLine {
        val report = it.split(Regex("\\s+")).map { it.toInt() }
        if (isReportSafe(report)) {
            safe++
        } else {
            val permutations = mutableListOf<List<Int>>()
            for (i in report.indices) {
                var permutation = report.toMutableList()
                permutation.removeAt(i)
                permutations.add(permutation)
            }
            for (permutation in permutations) {
                if (isReportSafe(permutation)) {
                    safe++
                    break
                }
            }
        }
    }
    println(safe)
}

fun main() {
    part1()
    part2()
}