package day08

import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.sqrt

val input = Path("src/Day08/day_08_input.txt")

fun main() {
    partOne()
}

//54600
fun partOne() {
    val lines = input.readLines()
    val src = lines.map { line -> line.split(",").map { it.toLong() } }
    val connectedBoxes = mutableMapOf<Double, Pair<Int, Int>>()

    for (i in 0 until src.size) {
        val a = src[i]
        for (j in i + 1 until src.size) {
            val b = src[j]
            val distance = sqrt(
                a.zip(b)
                    .sumOf { (a, b) -> (a - b) * (a - b) }.toDouble()
            )
            connectedBoxes.putIfAbsent(distance, Pair(i, j))
        }
    }
    val sortedBoxes = connectedBoxes.entries.sortedBy { it.key }
        .take(1000)
        .associate { it.toPair() }

    val circuits = mutableSetOf<MutableSet<Int>>()
    for ((i, value) in sortedBoxes.values.withIndex()) {
        val inCircuit = circuits.firstOrNull { list ->
            list.contains(value.first) || list.contains(value.second)
        } != null
        if (!inCircuit) {
            circuits.add(mutableSetOf(value.first, value.second))
        }
        for (j in i + 1 until sortedBoxes.size) {
            val innerValue = sortedBoxes.values.elementAtOrNull(j) ?: continue
            circuits.firstOrNull { list ->
                list.contains(innerValue.first) || list.contains(innerValue.second)
            }?.let {
                it.add(innerValue.first)
                it.add(innerValue.second)
            }
        }
    }

    val result = circuits.map { it.size }.sortedDescending().take(3).fold(1) { acc, i ->
        acc * i
    }
    println(result)
}
