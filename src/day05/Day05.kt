package day05

import kotlin.io.path.Path
import kotlin.io.path.readLines

val input = Path("src/Day05/day_05_input.txt")

fun main() {
    val lines = input.readLines()
    val ranges = lines.takeWhile { it.isNotEmpty() && it.contains("-") }
        .map { str ->
            val (a, b) = str.split("-")
                .map { it.toLong() }
            a to b
        }
    val ids = lines.takeLastWhile { it.isNotEmpty() && !it.contains("-") }.map { it.toLong() }

    // part one
    partOne(ids, ranges)

    // part two
    partTwo(ranges)
}

// 645
private fun partOne(ids: List<Long>, ranges: List<Pair<Long, Long>>) {
    var count = 0
    for (id in ids) {
        for (range in ranges) {
            if (id in range.first..range.second) {
                count++
                break
            }
        }
    }
    println(count)
}

// 369761800782619
private fun partTwo(ranges: List<Pair<Long, Long>>) {
    val sortedRanges = ranges.sortedBy { it.first }
    val combinedRanges = mutableSetOf<Pair<Long, Long>>()
    for (range in sortedRanges) {
        val inputStart = range.first
        val inputEnd = range.second
        var startToReplace: Long? = null
        var endToReplace: Long? = null
        var newStart = inputStart
        var newEnd = inputEnd
        for (combined in combinedRanges) {
            val existingStart = combined.first
            val existingEnd = combined.second
            if (inputStart <= existingStart && inputEnd >= existingEnd) {
                startToReplace = existingStart
                endToReplace = existingEnd
                newStart = inputStart
                newEnd = inputEnd
            } else if (inputStart in existingStart..existingEnd && inputEnd in existingStart..existingEnd) {
                newStart = existingStart
                newEnd = existingEnd
            } else if (inputStart in existingStart..existingEnd) {
                newStart = existingStart
            } else if (inputEnd in existingStart..existingEnd) {
                newEnd = existingEnd
            } else if (existingStart in inputStart..inputEnd && existingEnd in inputStart..inputEnd) {
                startToReplace = existingStart
                endToReplace = existingEnd
                newStart = inputStart
                newEnd = inputEnd
            } else if (existingStart in inputStart..inputEnd) {
                startToReplace = existingStart
                newStart = inputStart
            } else if (existingEnd in inputStart..inputEnd) {
                endToReplace = existingEnd
                newEnd = inputEnd
            }
        }
        if (startToReplace != null && endToReplace != null) {
            combinedRanges.removeAll { it.first == startToReplace }
            combinedRanges.removeAll { it.second == endToReplace }
            combinedRanges.add(newStart to newEnd)
        } else if (startToReplace != null) {
            combinedRanges.removeAll { it.first == startToReplace }
            combinedRanges.add(newStart to newEnd)
        } else if (endToReplace != null) {
            combinedRanges.removeAll { it.second == endToReplace }
            combinedRanges.add(newStart to newEnd)
        } else {
            combinedRanges.removeAll { it.first == newStart }
            combinedRanges.removeAll { it.second == newEnd }
            combinedRanges.add(newStart to newEnd)
        }
    }
    var count = 0L
    for (range in combinedRanges) {
        count += range.second - range.first + 1
    }
    println(count)
}
