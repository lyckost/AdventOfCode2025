package Day04

import kotlin.io.path.Path
import kotlin.io.path.readLines

val input = Path("src/Day04/day_04_input.txt").readLines()

fun main() {
    val lines = input
    partOne(lines)
    partTwo(lines)
}

// 1578
private fun partOne(lines: List<String>) {
    val textMatrix = lines.map { string ->
        string.toCharArray().map { it }
    }
    var count = 0
    val rowSize = textMatrix.first().size
    val columnSize = textMatrix.size
    for (i in 0..rowSize - 1) {
        for (j in 0..columnSize - 1) {
            if (textMatrix[j][i] == '@') {
                val topRowCount = textMatrix.getOrNull(j - 1)?.filterIndexed { index, c ->
                    index in (i - 1)..(i + 1) && c == '@'
                }?.count() ?: 0
                val bottomRowCount = textMatrix.getOrNull(j + 1)?.filterIndexed { index, c ->
                    index in (i - 1)..(i + 1) && c == '@'
                }?.count() ?: 0
                val leftCount = if (textMatrix[j].getOrNull(i - 1) == '@') {
                    1
                } else {
                    0
                }
                val rightCount = if (textMatrix[j].getOrNull(i + 1) == '@') {
                    1
                } else {
                    0
                }
                if (topRowCount + bottomRowCount + leftCount + rightCount < 4) {
                    count++
                }
            }
        }
    }
    println(count)
}

// 10132
private fun partTwo(lines: List<String>) {
    val textMatrix: MutableList<MutableList<Char>> = lines
        .map { string ->
            string.toCharArray().map { it }.toMutableList()
        }.toMutableList()
    var count = 0
    val rowSize = textMatrix.first().size
    val columnSize = textMatrix.size
    val indexesToRemove = mutableListOf<Pair<Int, Int>>()
    while (true) {
        for (i in 0..rowSize - 1) {
            for (j in 0..columnSize - 1) {
                if (textMatrix[j][i] == '@') {
                    val topRowCount = textMatrix.getOrNull(j - 1)?.filterIndexed { index, c ->
                        index in (i - 1)..(i + 1) && c == '@'
                    }?.count() ?: 0
                    val bottomRowCount = textMatrix.getOrNull(j + 1)?.filterIndexed { index, c ->
                        index in (i - 1)..(i + 1) && c == '@'
                    }?.count() ?: 0
                    val leftCount = if (textMatrix[j].getOrNull(i - 1) == '@') {
                        1
                    } else {
                        0
                    }
                    val rightCount = if (textMatrix[j].getOrNull(i + 1) == '@') {
                        1
                    } else {
                        0
                    }
                    if (topRowCount + bottomRowCount + leftCount + rightCount < 4) {
                        indexesToRemove.add(j to i)
                        count++
                    }
                }
            }
        }
        for (i in 0..rowSize - 1) {
            for (j in 0..columnSize - 1) {
                if (indexesToRemove.contains(j to i)) {
                    textMatrix[j][i] = '.'
                }
            }
        }
        if (indexesToRemove.isEmpty()) {
            break
        }
        indexesToRemove.clear()
    }

    println(count)
}
