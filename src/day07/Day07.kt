package day07

import kotlin.io.path.Path
import kotlin.io.path.readLines

val input = Path("src/Day07/day_07_input.txt")

fun main() {
    partOne()
    partTwo()
}

// 1539
fun partOne() {
    val lines = input.readLines()
    val matrix = lines.map { string ->
        string.toCharArray().map { it }
    }.toMutableList()
    var count = 0
    var i = 0
    val beamPositionsInRow = mutableSetOf<Int>()
    while (i < matrix.size) {
        var j = 0
        while (j < matrix[i].size) {
            if (matrix[i][j] == 'S') {
                beamPositionsInRow.add(j)
                break
            }
            if (matrix[i][j] == '^') {
                if (beamPositionsInRow.contains(j)) {
                    beamPositionsInRow.remove(j)
                    beamPositionsInRow.add(j - 1)
                    beamPositionsInRow.add(j + 1)
                    count++
                }
            }
            j++
        }
        i++
    }
    println(count)
}

// 6479180385864
fun partTwo() {
    val lines = input.readLines()
    val matrix = lines.map { string ->
        string.toCharArray().map { it.toString() }.toMutableList()
    }.toMutableList()
    var i = 0
    val beamPositions = mutableSetOf<Pair<Int, Int>>()
    val hashMap = mutableMapOf<Position, Long>()
    while (i < matrix.size) {
        var j = 0
        while (j < matrix[i].size) {
            if (matrix[i][j] == "S") {
                beamPositions.add(i + 1 to j)
                val position = Position(i + 1, j)
                hashMap[position] = hashMap.getOrDefault(position, 0L) + 1L
                matrix[i + 1][j] = hashMap[position].toString()
                break
            }
            if (matrix[i][j] == "^") {
                val currentPosition = Position(i - 1, j)
                val currentValue = hashMap.getOrDefault(currentPosition, 0L)
                val leftResult = hashMap.getOrDefault(Position(i, j - 1), 0L) + currentValue
                hashMap[Position(i, j - 1)] = leftResult
                val rightResult = hashMap.getOrDefault(Position(i, j + 1), 0L) + currentValue
                hashMap[Position(i, j + 1)] = rightResult
                matrix[i][j - 1] = leftResult.toString()
                matrix[i][j + 1] = rightResult.toString()
            } else if ("\\d+".toRegex().matches(matrix.getOrNull(i - 1)?.getOrNull(j).orEmpty())) {
                hashMap[Position(i, j)] = hashMap.getOrDefault(Position(i, j), 0L) +
                        hashMap.getOrDefault(Position(i - 1, j), 0L)
                matrix[i][j] = hashMap[Position(i, j)].toString()
            }
            j++
        }
        i++
    }

    println(hashMap.filter { it.key.x == matrix.size - 1 }.values.sum())
}

data class Position(val x: Int, val y: Int)
