package uk.co.veryhappythings.common

import java.io.File

data class Point(val x: Int, val y: Int)

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
