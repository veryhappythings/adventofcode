package uk.co.veryhappythings.day8

import uk.co.veryhappythings.common.Vec2
import uk.co.veryhappythings.common.printGrid
import uk.co.veryhappythings.common.readGrid

fun part1() {
    val grid = readGrid("src/main/resources/day8/input2.txt")
    printGrid(grid)

    val pairs = mutableListOf<List<Vec2>>()
    val antinodes = mutableSetOf<Vec2>()
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            val symbol = grid[y][x]
            // If we've found an antenna
            if (symbol != '.') {
                val first = Vec2(x, y)
                println("Found $first")
                // Go looking for a matching one in the rest of the grid
                // We don't need to start from 0 for y, but we do for x
                for (y2 in y..grid.size - 1) {
                    for (x2 in 0 ..grid[y].size - 1) {
                        val second = Vec2(x2, y2)
                        // If we find a matching symbol, and we haven't seen it before (which must be true if y2 or x2 is greater than the first
                        if (grid[y2][x2] == symbol && (y2 > first.y || x2 > first.x)) {
                            // Make the pair - the order in which we go through the grid should mean pairs can't occur in the opposite order
                            val pair = listOf(first, second)
                            // If we haven't already seen the pair, we can calculate the antinodes for it
                            if (!pairs.contains(pair)) {
                                // do the distance from point A to B, then add it on to B. Then the distance from point B to A, then add it on to A
                                val distanceFirstToSecond = second - first
                                val distanceSecondToFirst = first - second
                                val antinode1 = second + distanceFirstToSecond
                                val antinode2 = first + distanceSecondToFirst
                                for (antinode in listOf(antinode1, antinode2)) {
                                    if ((antinode.y >= 0) && (antinode.x >= 0) && (antinode.y < grid.size) && (antinode.x < grid[antinode.y].size)) {
                                        antinodes.add(antinode)
                                    }
                                }
                                pairs.add(pair)
                            }
                        }
                    }
                }
            }
        }
    }
    println(antinodes.size)
}

fun part2() {

}

fun main() {
    part1()
    println("------")
    part2()
}