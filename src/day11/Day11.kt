package day11

import kotlin.io.path.Path
import kotlin.io.path.readLines

val input = Path("src/Day11/day_11_input.txt")

fun main() {
    partOne()
    partTwo()
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


private data class CacheKey(val name: String, val visitedDac: Boolean, val visitedFft: Boolean)

private val cache = mutableMapOf<CacheKey, Long>()

// 385912350172800
private fun partTwo() {
    val lines = input.readLines()
    val src = lines.map { line ->
        val key = line.substringBefore(":")
        val entries = line.substringAfter(":").split(" ").filter { it.isNotBlank() }
        key to entries
    }.groupBy({ it.first }, { it.second })
        .mapValues { (k, v) -> v.flatten().toSet() }
    val count = findOutTwo(startKey = "svr", src)
    println(count)
}

private fun findOutTwo(
    startKey: String,
    src: Map<String, Set<String>>,
    visitedDac: Boolean = false,
    visitedFft: Boolean = false
): Long {
    return cache.getOrPut(CacheKey(startKey, visitedDac, visitedFft)) {
        var result = 0L
        for (key in src.keys) {
            if (key == startKey) {
                val values = src.getValue(key)
                for (value in values) {
                    if (value == "out" && visitedDac && visitedFft) {
                        result = 1L
                        break
                    }
                    result += findOutTwo(
                        startKey = value,
                        src = src.filterKeys { it != startKey },
                        visitedDac = value == "dac" || visitedDac,
                        visitedFft = value == "fft" || visitedFft
                    )
                }
            }
        }
        result
    }
}
