package day03

import kotlin.io.path.Path
import kotlin.io.path.readLines

val input = Path("src/Day03/day_03_input.txt").readLines()


fun main() {
    val numbers = input.map { it.toCharArray().map { char -> char.digitToInt().toLong() } }
    // 17435
    solve(numbers, numberOfDigits = 2)
    // 172886048065379
    solve(numbers, numberOfDigits = 12)
}

private fun solve(numbers: List<List<Long>>, numberOfDigits: Int = 2) {
    var sum = 0L
    for (line in numbers) {
        val maxList = mutableListOf<Long>()
        var lastMaxIndex = 0
        var count = numberOfDigits
        while (count > 0) {
            var max = 0L
            for (i in lastMaxIndex..line.size - count) {
                if (line[i] == 9L) {
                    max = line[i]
                    lastMaxIndex = i
                    break
                } else if (line[i] > max) {
                    max = line[i]
                    lastMaxIndex = i
                }
            }
            lastMaxIndex++
            maxList.add(max)
            count--
        }
        sum += maxList.fold(0L) { acc, i ->
            acc * 10L + i
        }
    }

    println(sum)
}
