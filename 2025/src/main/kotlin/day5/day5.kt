package uk.co.veryhappythings.day5

import java.io.File

data class Range(val start: Long, val end: Long) {
    // Returns true if this range entirely contains the other range
    fun contains(other: Range): Boolean {
        return other.start >= start && other.end <= end
    }

    // Returns true if this range overlaps with the other range
    // i.e. other.start is between start and end, or other.end is between start and end
    // Or it's entirely contained within this range, or we're entirely contained within the other range
    fun overlaps(other: Range): Boolean {
        return (other.start in start..end) ||
                (other.end in start..end) ||
                (start in other.start..other.end) ||
                (end in other.start..other.end)
    }
}


fun part1() {
    val stream = File("src/main/resources/day5/input2.txt").inputStream()
    val ranges = mutableListOf<Range>()
    val products = mutableListOf<Long>()
    val reader = stream.bufferedReader()
    reader.forEachLine {
        val parts = it.split("-")
        if (parts.size == 2) {
            ranges.add(Range(parts[0].toLong(), parts[1].toLong()))
        } else if (it != "") {
            products.add(it.toLong())
        }
    }
    var fresh = 0
    for (product in products) {
        for (range in ranges) {
            if (product >= range.start && product <= range.end) {
                fresh++
                break
            }
        }
    }
    println(fresh)
}

fun mergeRanges(ranges: MutableList<Range>): MutableList<Range> {
    val inclusiveRanges = mutableListOf<Range>()
    for (range in ranges) {
        var isIncluded = false
        for (included in inclusiveRanges) {
            // If the range we're considering is covered entirely by an existing inclusive range, skip it
            if (included.contains(range)) {
                isIncluded = true
                break
            }
            // If the range we're considering overlaps with an existing inclusive range, merge them
            if (included.overlaps(range)) {
                val newStart = minOf(included.start, range.start)
                val newEnd = maxOf(included.end, range.end)
                inclusiveRanges.remove(included)
                inclusiveRanges.add(Range(newStart, newEnd))
                isIncluded = true
                break
            }
        }
        if (!isIncluded) {
            inclusiveRanges.add(range)
        }
    }
    return inclusiveRanges
}

fun part2() {
    val stream = File("src/main/resources/day5/input2.txt").inputStream()
    val ranges = mutableListOf<Range>()
    val products = mutableListOf<Long>()
    val reader = stream.bufferedReader()
    reader.forEachLine {
        val parts = it.split("-")
        if (parts.size == 2) {
            ranges.add(Range(parts[0].toLong(), parts[1].toLong()))
        } else if (it != "") {
            products.add(it.toLong())
        }
    }

    var previousBestSize = 0
    var finalRangeList = ranges
    while (previousBestSize != finalRangeList.size) {
        println("Merging ranges, current count: ${previousBestSize}. ${finalRangeList.size}")
        previousBestSize = finalRangeList.size
        finalRangeList = mergeRanges(finalRangeList)
        println("Done Merging ranges, current count: ${previousBestSize}. ${finalRangeList.size}")
    }
    println("Inclusive ranges: ${finalRangeList.size}")
    var fresh: Long = 0
    for (range in finalRangeList) {
        fresh += (range.end - range.start + 1)
    }
    println(fresh)
}

fun main() {
    part1()
    part2()
}
