package Day01

import kotlin.io.path.Path
import kotlin.io.path.readLines

val input = Path("src/Day01/day_01_input.txt")

fun main() {
    val lines = input.readLines()
    partOne(lines)
    partTwo(lines)
}

private fun partOne(lines: List<String>) {
    var count = 0
    var dial = 50
    for (line in lines) {
        val direction = line.first()
        val number = line.substringAfter(direction).toInt() % 100
        if (direction == 'L') {
            dial -= number
            if (dial < 0) {
                dial += 100
            }
            if (dial == 0) count++
        } else {
            dial += number
            if (dial > 99) {
                dial -= 100
            }
            if (dial == 0) count++
        }
    }
    println(count)
}

fun partTwo(lines: List<String>) {
    var count = 0
    var dial = 50
    for (line in lines) {
        val direction = line.first()
        var number = line.substringAfter(direction).toInt()
        if (direction == 'L') {
            if (dial == 0) count--
            count += number / 100
            number %= 100
            dial -= number
            if (dial < 0) {
                dial += 100
                count++
            } else if (dial == 0) {
                count++
            }
        } else {
            count += number / 100
            number %= 100
            dial += number
            if (dial > 99) {
                dial -= 100
                count++
            }
        }
    }
    println(count)
}