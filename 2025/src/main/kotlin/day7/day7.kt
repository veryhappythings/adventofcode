package uk.co.veryhappythings.day7

import uk.co.veryhappythings.common.Point
import uk.co.veryhappythings.common.copyGrid
import uk.co.veryhappythings.common.printGrid
import uk.co.veryhappythings.common.readGrid

fun part1() {
    val grid = readGrid("src/main/resources/day7/input2.txt")
    val s = Point(grid[0].indexOf('S'), 0)

    // First step
    grid[1][s.x] = '|'
    printGrid(grid)

    var splits = 0
    var currentRow = 2
    while (currentRow < grid.size) {
        for (x in grid[currentRow].indices) {
            if (grid[currentRow - 1][x] == '|') {
                if (grid[currentRow][x] == '^') {
                    grid[currentRow][x-1] = '|'
                    grid[currentRow][x+1] = '|'
                    splits++
                } else {
                    grid[currentRow][x] = '|'
                }
            }
        }
        currentRow++
    }
    printGrid(grid)
    println(splits)
}

var memo = mutableMapOf<Point, Long>()

// follow a path to the bottom. Return all timelines as a set of paths
fun followPath(grid: List<List<Char>>, path: List<Point>): Long {
    // Memoize this - For any given point the timelines it generates is deterministic
    if (path[path.size-1] in memo) {
        return memo[path[path.size-1]]!!
    }
    var result: Long = 0
    val currentRow = path[path.size-1].y + 1
    var newPath = path.toMutableList()


    for (y in currentRow ..< grid.size) {
        for (x in grid.indices) {
            // If the cell above us is in the path (i.e. is a |)
            if (Point(x, y - 1) in newPath) {
                if (grid[y][x] == '^') {
                    val leftPath = newPath.toMutableList()
                    leftPath.add(Point(x - 1, y))
                    result += followPath(grid, leftPath)

                    val rightPath = newPath.toMutableList()
                    rightPath.add(Point(x + 1, y))
                    result += followPath(grid, rightPath)
                } else {
                    newPath.add(Point(x, y))
                    if (y == grid.size - 1) {
                        result += 1
                    }
                }
            }
        }
    }
    memo[path[path.size-1]] = result
    return result
}

fun part2() {
    val grid = readGrid("src/main/resources/day7/input2.txt")
    val s = Point(grid[0].indexOf('S'), 0)

    val paths = followPath(grid, mutableListOf(Point(s.x, 1)))
    println(paths)
}

fun main() {
    part1()
    part2()
}
