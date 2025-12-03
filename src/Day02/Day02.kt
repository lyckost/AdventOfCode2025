package Day02

import kotlin.io.path.Path
import kotlin.io.path.readText

val input = Path("src/Day02/day_02_input.txt").readText()

fun main() {
    val ranges = input
        .split(",")
        .map {
            val (a, b) = it.split("-")
            a.toLong() to b.toLong()
        }
    val partOneResult = partOne(ranges)
    val partTwoResult = partTwo(ranges)
    assert(partOneResult == 44854383294)
    assert(partTwoResult == 55647141923)
}

// 44854383294
private fun partOne(ranges: List<Pair<Long, Long>>): Long {
    val invalidIds = mutableListOf<Long>()
    for (range in ranges) {
        val start = range.first
        val end = range.second
        for (i in start..end) {
            val text = i.toString()
            val size = text.length
            if (size % 2 != 0) continue
            val a = text.take(size / 2)
            val b = text.takeLast(size / 2)
            if (a == b) {
                invalidIds.add(i)
            }
        }
    }
    val result = invalidIds.sum()
    println(result)
    return result
}

// 55647141923
private fun partTwo(ranges: List<Pair<Long, Long>>): Long {
    val invalidIds = mutableListOf<Long>()
    for (range in ranges) {
        val start = range.first
        val end = range.second
        for (i in start..end) {
            val text = i.toString()
            for (segment in 1.. text.length / 2) {
                val chunks = text.chunked(segment)
                if (chunks.all { it == chunks.first() }) {
                    invalidIds.add(i)
                    break
                }
            }
        }
    }
    val result = invalidIds.sum()
    println(result)
    return result
}
