package uk.co.veryhappythings.day6

import uk.co.veryhappythings.common.Point
import uk.co.veryhappythings.common.readGrid
import uk.co.veryhappythings.common.printGrid


fun part1() {
    val grid = readGrid("src/main/resources/day6/input2.txt")
    printGrid(grid)
    var guardPosition = Point(0, 0)
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            if (grid[y][x] == '^') {
                guardPosition = Point(x, y)
            }
        }
    }
    val guardPositions = mutableSetOf<Point>()
    do {
        guardPositions.add(guardPosition)
        val direction = when (grid[guardPosition.y][guardPosition.x]) {
            '^' -> Point(0, -1)
            'v' -> Point(0, 1)
            '<' -> Point(-1, 0)
            '>' -> Point(1, 0)
            else -> throw Exception("Invalid guard char")
        }
        val inFront = Point(guardPosition.x + direction.x, guardPosition.y + direction.y)
        if (inFront.x < 0 || inFront.x >= grid[0].size || inFront.y < 0 || inFront.y >= grid.size) {
            break
        }
        val charInFront = grid[inFront.y][inFront.x]
        if (charInFront == '#') {
            grid[guardPosition.y][guardPosition.x] = when (grid[guardPosition.y][guardPosition.x]) {
                '^' -> '>'
                '>' -> 'v'
                'v' -> '<'
                '<' -> '^'
                else -> throw Exception("Invalid guard char")
            }
        } else {
            val guardChar = grid[guardPosition.y][guardPosition.x]
            grid[guardPosition.y][guardPosition.x] = '.'
            guardPosition = inFront
            grid[guardPosition.y][guardPosition.x] = guardChar
        }
    } while (guardPosition.x >= 0 && guardPosition.x < grid[0].size && guardPosition.y >= 0 && guardPosition.y < grid.size)
    println("Final position: $guardPosition")
    println("Number of unique positions: ${guardPositions.size}")
}

fun part2() {

}

fun main() {
    part1()
    part2()
}
