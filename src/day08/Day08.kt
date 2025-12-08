package day08

import kotlin.io.path.Path
import kotlin.io.path.readLines

val input = Path("src/Day08/day_08_input.txt")

fun main() {
//    partOne()
    partOneUnion()
}

//54600
fun partOne() {
    val lines = input.readLines()
    val src = lines.map { line -> line.split(",").map { it.toLong() } }
    val connectedBoxes = mutableMapOf<Long, Pair<Int, Int>>()
    for (i in 0 until src.size) {
        val a = src[i]
        for (j in i + 1 until src.size) {
            val b = src[j]
            val distance = a.zip(b)
                .sumOf { (a, b) -> (a - b) * (a - b) }
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

fun partOneUnion() {
    val lines = input.readLines()
    val src = lines.map { line -> line.split(",").map { it.toLong() } }
    val connectedBoxes = mutableMapOf<Long, Pair<Int, Int>>()
    for (i in 0 until src.size) {
        val a = src[i]
        for (j in i + 1 until src.size) {
            val b = src[j]
            val distance = a.zip(b)
                .sumOf { (a, b) -> (a - b) * (a - b) }
            connectedBoxes.putIfAbsent(distance, Pair(i, j))
        }
    }

    val sortedBoxes = connectedBoxes.entries.sortedBy { it.key }
        .take(10)
        .associate { it.toPair() }

    val uf = UnionFind(connectedBoxes.size)
    for ((key, value) in sortedBoxes) {
        uf.union(value.first, value.second)
    }

    val groups = mutableMapOf<Int, MutableList<Int>>()

    for (i in 0 until connectedBoxes.size) {
        val root = uf.find(i)
        groups.getOrPut(root) { mutableListOf() }.add(i)
    }

    for ((root, members) in groups) {
        println("Root $root: $members size is ${members.size}")
    }

    val result = groups.map { it.value.size }.sortedDescending().take(3).fold(1) { acc, i ->
        acc * i
    }
    println("result = $result")
}

class UnionFind(n: Int) {
    val parent = IntArray(n) { it }

    fun find(x: Int): Int {
        if (parent[x] != x) {
            parent[x] = find(parent[x])
        }
        return parent[x]
    }

    fun union(a: Int, b: Int) {
        val rootA = find(a)
        val rootB = find(b)

        parent[rootA] = rootB
    }
}
