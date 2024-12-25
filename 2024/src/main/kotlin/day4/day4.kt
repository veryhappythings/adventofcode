package uk.co.veryhappythings.day4

import java.io.File

import uk.co.veryhappythings.common.Point
import uk.co.veryhappythings.common.readGrid

fun possiblePaths(grid: MutableList<MutableList<Char>>, pathRemainder: String): MutableList<MutableList<Point>> {
    if (pathRemainder.isEmpty()) {
        throw IllegalArgumentException("pathRemainder is empty")
    }

    val paths = mutableListOf<MutableList<Point>>()
    // Look through the grid, if we find our starting letter, we can start looking for the next one
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            if (grid[y][x] == pathRemainder[0]) {
                paths += travel(grid, x, y, -1, -1, pathRemainder)
                paths += travel(grid, x, y, -1, 0, pathRemainder)
                paths += travel(grid, x, y, -1, 1, pathRemainder)
                paths += travel(grid, x, y, 1, 0, pathRemainder)
                paths += travel(grid, x, y, 1, -1, pathRemainder)
                paths += travel(grid, x, y, 0, -1, pathRemainder)
                paths += travel(grid, x, y, 0, 1, pathRemainder)
                paths += travel(grid, x, y, 1, 1, pathRemainder)
                paths.removeIf { it.isEmpty() }
            }
        }
    }
    return paths
}

fun travel(grid: MutableList<MutableList<Char>>, x: Int, y: Int, directionX: Int, directionY: Int, pathRemainder: String): MutableList<Point> {
    // If we've hit the end of the word, it's a path!
    if (pathRemainder.isEmpty()) {
        return mutableListOf<Point>(Point(x, y))
    }
    // If we're outside the grid, it's not
    if (x < 0) {
        return mutableListOf<Point>()
    }
    if (y < 0) {
        return mutableListOf<Point>()
    }
    if (y >= grid.size) {
        return mutableListOf<Point>()
    }
    if (x >= grid[y].size) {
        return mutableListOf<Point>()
    }
    // If we're at our target letter, we can look for the next one
    if (pathRemainder[0] == grid[y][x]) {
        // If we found the last letter, we've found a path and should return it
        if (pathRemainder.length == 1) {
            return mutableListOf<Point>(Point(x, y))
        }
        // Otherwise we're on, e.g. M, and we want to keep travelling in the same direction looking for AS.
        // If it finds it, it'll return the path, otherwise it'll return an empty list
        val path = travel(grid, x + directionX, y + directionY, directionX, directionY, pathRemainder.substring(1))
        // If it's come back not empty, add ourselves and pass it up the chain
        if (path.isNotEmpty()) {
            path.add(0, Point(x, y))
            return path
        }
    }
    return mutableListOf<Point>()
}

fun part1() {
    val grid = readGrid("src/main/resources/day4/input2.txt")
    var total = 0
    val paths = possiblePaths(grid, "XMAS")
    println(paths.size)
}

fun part2() {
    val grid = readGrid("src/main/resources/day4/input2.txt")
    var total = 0
    val paths = possiblePaths(grid, "MAS")
    println(paths.size)
    // It's too slow to check every path against every path for a cross, so...
    // Build a map of paths by their centre point
    val pathsByCentre = mutableMapOf<Point, MutableList<MutableList<Point>>>()
    for (path in paths) {
        val centre = path[1]
        if (pathsByCentre.containsKey(centre)) {
            pathsByCentre[centre]!!.add(path)
        } else {
            pathsByCentre[centre] = mutableListOf(path)
        }
    }
    val crossingPaths = mutableListOf<MutableList<Point>>()
    for (path in paths) {
        // Ignore any path that's already in an X
        if (crossingPaths.contains(path)) {
            continue
        }
        for (path2 in pathsByCentre[path[1]]!!) {
            // Ignore identical paths
            if (path == path2) {
                continue
            }
            // Ignore any path that's already in an X
            if (crossingPaths.contains(path2)) {
                continue
            }
            val direction = Point(path[1].x - path[0].x, path[1].y - path[0].y)
            val direction2 = Point(path2[1].x - path2[0].x, path2[1].y - path2[0].y)
            // If both directions are diagonal, it must be an X
            if (direction.x != 0 && direction.y != 0 && direction2.x != 0 && direction2.y != 0) {
                println("Crossing paths: $path $path2")
                total += 1
                crossingPaths.add(path)
                crossingPaths.add(path2)
            }
        }
    }
    println(total)
}

fun main() {
    part1()
    println("--------")
    part2()
}