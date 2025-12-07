package uk.co.veryhappythings.common

import java.io.File

data class Point(val x: Int, val y: Int)

data class Vec2(val x: Int, val y: Int) {
    operator fun times(other: Vec2): Vec2 {
        return Vec2(x * other.x, y * other.y)
    }

    operator fun times(value: Int): Vec2 {
        return Vec2(x * value, y * value)
    }

    operator fun plus(other: Vec2): Vec2 {
        return Vec2(x + other.x, y + other.y)
    }

    operator fun minus(other: Vec2): Vec2 {
        return Vec2(x - other.x, y - other.y)
    }
}

/**
 * Read a grid from a file
 * The grid is accessible using `grid[y][x]`
 * @param filename The file to read
 * @param grid The grid
 */
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

fun printGrid(grid: List<List<Char>>) {
    for (y in grid.indices) {
        for (x in grid[y].indices) {
            print(grid[y][x])
        }
        println()
    }
}

fun copyGrid(grid: List<List<Char>>) : MutableList<MutableList<Char>> {
    val newGrid = mutableListOf<MutableList<Char>>()
    for (y in grid.indices) {
        val newRow = mutableListOf<Char>()
        for (x in grid[y].indices) {
            newRow.add(grid[y][x])
        }
        newGrid.add(newRow)
    }
    return newGrid
}
