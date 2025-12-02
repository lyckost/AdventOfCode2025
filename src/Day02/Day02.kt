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
    partOne(ranges)
    partTwo(ranges)
}

private fun partOne(ranges: List<Pair<Long, Long>>) {
    val repetitions = mutableListOf<Long>()
    for (range in ranges) {
        repetitions.addAll(findRepetitionsInRange(range.first, range.second))
    }
    val result = repetitions.sum()
    println(result)
}

private fun findRepetitionsInRange(start: Long, end: Long): List<Long> {
    val result = mutableListOf<Long>()
    for (i in start..end) {
        if (isRepetition(i)) result.add(i)
    }
    return result
}

private fun isRepetition(x: Long): Boolean {
    val text = x.toString()
    val size = text.length
    if (size % 2 != 0) return false
    val a = text.take(size / 2)
    val b = text.takeLast(size / 2)
    return a == b
}

private fun partTwo(ranges: List<Pair<Long, Long>>) {
    val repetitions = mutableListOf<Long>()
    for (range in ranges) {
        repetitions.addAll(findRepetitionsInRangePartTwo(range.first, range.second))
    }
    val result = repetitions.sum()
    println(result)
}

private fun findRepetitionsInRangePartTwo(start: Long, end: Long): List<Long> {
    val result = mutableListOf<Long>()
    for (i in start..end) {
        if (isRepetitionPartTwo(i)) result.add(i)
    }
    return result
}

private fun isRepetitionPartTwo(x: Long): Boolean {
    val text = x.toString()
    val digits = text.map { it.digitToInt() }
    val matches = digits.runningFold(0) { acc, i -> acc * 10 + i }.dropLast(1)
    for (match in matches) {
        val matchAsText = match.toString()
        val splitResult = text.split(matchAsText)
        if (splitResult.all { it.isEmpty()}) {
            return true
        }
    }
    return false
}
