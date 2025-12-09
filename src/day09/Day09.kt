package day09

import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.absoluteValue

val input = Path("src/Day09/day_09_input.txt")

fun main() {
    partOne()
}

// 4755064176
fun partOne() {
    val lines = input.readLines()
    val src = lines.map { line -> line.split(",").map { it.toLong() } }
    var maxSquare = 0L
    for (i in 0 until src.size) {
        val a = src[i]
        for (j in i + 1 until src.size) {
            val b = src[j]
            val square = ((a[0] - b[0]).absoluteValue + 1) * ((a[1] - b[1]).absoluteValue + 1)
            if (square > maxSquare) {
                maxSquare = square
            }
        }
    }
    println(maxSquare)
}
