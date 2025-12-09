package day06

import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.max

val input = Path("src/Day06/day_06_input.txt")

fun main() {
    partOne()
    partTwo()
}

//7098065460541
private fun partOne() {
    val lines = input.readLines()
    val textMatrix = lines.map { string ->
        string.trim().split("\\s+".toRegex())
            .map { it }
    }
    val numbers = mutableListOf<Long>()
    val rowSize = textMatrix.first().size
    val columnSize = textMatrix.size
    var sum = 0L
    for (i in 0..rowSize - 1) {
        for (j in 0..columnSize - 1) {
            if (j != columnSize - 1) {
                numbers.add(textMatrix[j][i].toLong())
            } else {
                val sign = textMatrix[j][i]
                when (sign) {
                    "*" -> {
                        sum += numbers.reduce { acc, num -> acc * num }
                    }
                    else -> {
                        sum += numbers.sum()
                    }
                }
            }
        }
        numbers.clear()
    }
    println(sum)
}

//13807151830618
private fun partTwo() {
    val lines = input.readLines()
    val regex = Regex("^(?:\\d{3}|\\d{2} |\\d  {2})$")
    var maxRowSize = 0
    val textMatrix = lines.map { string ->
        string.split(regex)
            .map {
                val array = it.toCharArray()
                maxRowSize = max(maxRowSize, array.size)
                array
            }
    }.flatten()
    val numbers = mutableListOf<Long>()
    var sum = 0L
    var sign: String? = null
    for (i in 0..maxRowSize) {
        var number = 0L
        var numberFound = false

        for (j in 0..textMatrix.size - 2) {
            val char = textMatrix.getOrNull(j)?.getOrNull(i)?.toString() ?: break
            if (char.isNotBlank()) {
                number = number * 10L + char.toLong()
                numberFound = true
            }
        }
        if (sign == null) {
            sign = textMatrix[textMatrix.size - 1][i].toString()
        }

        if (numberFound) {
            numbers.add(number)
        }
        if (!numberFound) {
            when (sign) {
                "*" -> {
                    sum += numbers.reduce { acc, num -> acc * num }
                }
                "+" -> {
                    sum += numbers.sum()
                }
            }
            numbers.clear()
            sign = null
        }
    }
    println(sum)
}
