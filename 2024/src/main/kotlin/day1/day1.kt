package uk.co.veryhappythings.day1

import java.io.File
import java.io.FileReader

fun part2() {
    val stream = File("src/main/resources/day1/input2.txt").inputStream()
    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()
    val scores = mutableListOf<Int>()
    stream.bufferedReader().forEachLine {
        val items = it.split(Regex("\\s+"))
        list1.add(items[0].toInt())
        list2.add(items[1].toInt())
    }
    for (item: Int in list1) {
        var count = 0
        for (item2: Int in list2) {
            if (item2 == item) {
                count++
            }
        }
        scores.add(item * count)
    }
    println(scores.sum())
}

fun part1() {
    val stream = File("src/main/resources/day1/input2.txt").inputStream()
    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()
    val distances = mutableListOf<Int>()
    stream.bufferedReader().forEachLine {
        val items = it.split(Regex("\\s+"))
        list1.add(items[0].toInt())
        list2.add(items[1].toInt())
    }
    list1.sort()
    list2.sort()
    while (list1.size > 0) {
        val item1 = list1.removeFirst()
        val item2 = list2.removeFirst()
        var distance: Int
        if (item1 > item2) {
            distance = item1 - item2
        } else {
            distance = item2 - item1
        }
        distances.add(distance)
    }
    println(distances.sum())

}

fun main() {
    part1()
    part2()
}