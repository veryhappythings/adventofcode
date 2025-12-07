package uk.co.veryhappythings.day4

import uk.co.veryhappythings.common.readGrid
import uk.co.veryhappythings.common.printGrid
import uk.co.veryhappythings.common.Point
import uk.co.veryhappythings.common.copyGrid
import kotlin.collections.indices

fun part1() {
    val grid = readGrid("src/main/resources/day4/input2.txt")
    printGrid(grid)

    var accessible = mutableSetOf<Point>()

    for (y in grid.indices) {
        for (x in grid[y].indices) {
            if (grid[y][x] != '@') {
                continue
            }
            var surroundingCount = 0
            println("Checking point ($x,$y)")
            for (y2 in y-1..y+1) {
                for (x2 in x-1..x+1) {
                    if (y2 != y || x2 != x) {
                        if (y2 < grid.size && x2 < grid[y].size && y2 >= 0 && x2 >= 0) {
                            print(" Checking point ($x2,$y2) ${grid[y2][x2]}")
                            if (grid[y2][x2] == '@') {
                                surroundingCount += 1
                                print(" x")
                                if (surroundingCount >= 4) {
                                    break
                                }
                            }
                            println()
                        }
                    }
                }
            }
            if (surroundingCount < 4) {
                accessible.add(Point(x, y))
            }
        }
    }
    println("Accessible points: ${accessible.size}")
    for (point in accessible) {
        grid[point.y][point.x] = 'x'
    }
    printGrid(grid)
}

fun accessiblePoints(grid: MutableList<MutableList<Char>>) : Set<Point> {
    var accessible = mutableSetOf<Point>()

    for (y in grid.indices) {
        for (x in grid[y].indices) {
            if (grid[y][x] != '@') {
                continue
            }
            var surroundingCount = 0
            for (y2 in y-1..y+1) {
                for (x2 in x-1..x+1) {
                    if (y2 != y || x2 != x) {
                        if (y2 < grid.size && x2 < grid[y].size && y2 >= 0 && x2 >= 0) {
                            if (grid[y2][x2] == '@') {
                                surroundingCount += 1
                                if (surroundingCount >= 4) {
                                    break
                                }
                            }
                        }
                    }
                }
            }
            if (surroundingCount < 4) {
                accessible.add(Point(x, y))
            }
        }
    }
    return accessible
}

fun clearRolls(grid: MutableList<MutableList<Char>>, accessible: Set<Point>) : MutableList<MutableList<Char>> {
    for (point in accessible) {
        println("Clearing point (${point.x},${point.y})")
        grid[point.y][point.x] = '.'
    }
    return grid
}

fun part2() {
    var grid = readGrid("src/main/resources/day4/input2.txt")
    printGrid(grid)

    var removed = 0

    var previousGrid = mutableListOf<MutableList<Char>>()
    while (previousGrid != grid) {
        val accessible = accessiblePoints(grid)
        println("Accessible points: ${accessible.size}")
        removed += accessible.size
        previousGrid = copyGrid(grid)
        printGrid(grid)
        grid = clearRolls(grid, accessible)
        printGrid(grid)
        printGrid(previousGrid)
    }

    println("Total removed: $removed")
}

fun main() {
    //part1()
    part2()
}
