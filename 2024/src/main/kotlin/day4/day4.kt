package uk.co.veryhappythings.day4

import java.io.File

fun readGrid(filename: String) : MutableList<MutableList<Char>> {
    val stream = File(filename).inputStream()
    val grid = mutableListOf<MutableList<Char>>()
    stream.bufferedReader().forEachLine {
        val row = mutableListOf<Char>()
        for (char in it) {
            row.add(char)
        }
        grid.add(row)
    }
    return grid
}

fun possiblePaths(grid: MutableList<MutableList<Char>>, x: Int, y: Int): Int {
    return possiblePaths(grid, x, y, 0, 0, "XMAS")
}

fun possiblePaths(grid: MutableList<MutableList<Char>>, x: Int, y: Int, directionX: Int, directionY: Int, pathRemainder: String): Int {
    // If we've hit the end of the word, it's a path!
    if (pathRemainder.isEmpty()) {
        return 1
    }
    // If we're outside the grid, it's not
    if (x < 0) {
        return 0
    }
    if (y < 0) {
        return 0
    }
    if (y >= grid.size) {
        return 0
    }
    if (x >= grid[y].size) {
        return 0
    }
    // If we're at our target letter, we can look for the next one
    if (pathRemainder[0] == grid[y][x]) {
        var total = 0
        // If we're at the start, we can go in any direction
        if (directionX == 0 && directionY == 0) {
            total += possiblePaths(grid, x - 1, y - 1, -1, -1, pathRemainder.substring(1))
            total += possiblePaths(grid, x - 1, y, -1, 0, pathRemainder.substring(1))
            total += possiblePaths(grid, x - 1, y + 1, -1, 1, pathRemainder.substring(1))
            total += possiblePaths(grid, x + 1, y, 1, 0, pathRemainder.substring(1))
            total += possiblePaths(grid, x + 1, y - 1, 1, -1, pathRemainder.substring(1))
            total += possiblePaths(grid, x, y - 1, 0, -1, pathRemainder.substring(1))
            total += possiblePaths(grid, x, y + 1, 0, 1, pathRemainder.substring(1))
            total += possiblePaths(grid, x + 1, y + 1, 1, 1, pathRemainder.substring(1))
        } else {
            total += possiblePaths(grid, x + directionX, y + directionY, directionX, directionY, pathRemainder.substring(1))
        }
        return total
    }
    return 0
}

fun part1() {
    val grid = readGrid("src/main/resources/day4/input2.txt")
    var total = 0
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            total += possiblePaths(grid, x, y)
        }
        println(total)
    }

}

fun part2() {

}

fun main() {
    part1()
    println("--------")
    part2()
}