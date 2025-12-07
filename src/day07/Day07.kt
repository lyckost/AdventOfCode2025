package day07

import kotlin.io.path.Path
import kotlin.io.path.readLines

val input = Path("src/Day07/day_07_input.txt")

fun main() {
    partOne()
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
