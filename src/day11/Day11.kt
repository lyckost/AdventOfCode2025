package day11

import kotlin.io.path.Path
import kotlin.io.path.readLines

val input = Path("src/Day11/day_11_input.txt")

fun main() {
    partOne()
}

// 506
private fun partOne() {
    val lines = input.readLines()
    val src = lines.map { line ->
        val key = line.substringBefore(":")
        val entries = line.substringAfter(":").split(" ").filter { it.isNotBlank() }
        key to entries
    }.groupBy({ it.first }, { it.second })
        .mapValues { (k, v) -> v.flatten().toSet() }
    val count = findOut(startKey = "you", src, count = 0)
    println(count)
}

private fun findOut(startKey: String, src: Map<String, Set<String>>, count: Int): Int {
    var result = count
    for (key in src.keys) {
        if (key == startKey) {
            val values = src.getValue(key)
            for (value in values) {
                if (value == "out") {
                    return result + 1
                }
                result = findOut(value, src.filterKeys { it != startKey }, result)
            }
        }
    }
    return result
}
