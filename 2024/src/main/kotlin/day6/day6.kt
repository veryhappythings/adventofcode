package uk.co.veryhappythings.day6

import uk.co.veryhappythings.common.Point
import uk.co.veryhappythings.common.readGrid
import uk.co.veryhappythings.common.printGrid

fun doesGridLoop(grid: MutableList<MutableList<Char>>): Boolean {
    var guardPosition = getInitialGuardPosition(grid)
    val guardPositions = mutableListOf<Point>()
    var hasHitObstacle = false
    do {
        guardPositions.add(guardPosition)
        val direction = when (grid[guardPosition.y][guardPosition.x]) {
            '^' -> Point(0, -1)
            'v' -> Point(0, 1)
            '<' -> Point(-1, 0)
            '>' -> Point(1, 0)
            else -> throw Exception("Invalid guard char: ${guardPosition}, ${grid[guardPosition.y][guardPosition.x]}")
        }
        val inFront = Point(guardPosition.x + direction.x, guardPosition.y + direction.y)
        if (inFront.x < 0 || inFront.x >= grid[0].size || inFront.y < 0 || inFront.y >= grid.size) {
            break
        }
        val charInFront = grid[inFront.y][inFront.x]
        if (charInFront == 'O') {
            hasHitObstacle = true
        }
        if (charInFront == '#' || charInFront == 'O') {
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

        if (hasHitObstacle) {
            val timesThroughCurrentPosition = guardPositions.filter { it == guardPosition }.size
            val timesThroughPreviousPosition = guardPositions.filter { it == guardPositions[guardPositions.size - 1] }.size
            if (timesThroughCurrentPosition >= 3 && timesThroughPreviousPosition >= 3) {
                return true
            }
        }
    } while (guardPosition.x >= 0 && guardPosition.x < grid[0].size && guardPosition.y >= 0 && guardPosition.y < grid.size)
    return false
}

fun getInitialGuardPosition(grid: MutableList<MutableList<Char>>): Point {
    var guardPosition = Point(0, 0)
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            if (grid[y][x] == '^') {
                guardPosition = Point(x, y)
            }
        }
    }
    return guardPosition
}

fun part1() {
    val grid = readGrid("src/main/resources/day6/input2.txt")
    printGrid(grid)
    var guardPosition = getInitialGuardPosition(grid)
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
    val grid = readGrid("src/main/resources/day6/input2.txt")
    printGrid(grid)
    var guardPosition = getInitialGuardPosition(grid)
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

    var totalLoops = 0
    val freshGrid = readGrid("src/main/resources/day6/input2.txt")
    var initialGuardPosition = getInitialGuardPosition(freshGrid)
    for (position in guardPositions) {
        val clone = freshGrid.map { it.toMutableList() }.toMutableList()
        if (position != initialGuardPosition) {
            clone[position.y][position.x] = 'O'
            if (doesGridLoop(clone)) {
                totalLoops++
            }
        }
    }
    println("Total loops: $totalLoops")
}

fun main() {
    part1()
    part2()
}
