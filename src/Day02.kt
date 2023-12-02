import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {

        val maxs = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14
        )

        val res = input.sumOf { line ->

            val (game, values) = line.split(": ")
            val gameNo: Int = game.split(" ").last().toInt()

            if (checkValues(values.split("; "), maxs)) gameNo else 0
        }
        return res
    }

    fun part2(input: List<String>): Int {
        var maxs = mutableMapOf<String, Int>()

        return input.sumOf { line ->

            val (_, values) = line.split(": ")
            values.split("; ").flatMap { it.split(", ") }.forEach {
                val (c, color) = it.split(" ")

                val current = maxs.getOrDefault(color, -1)

                maxs[color] = max(c.toInt(), current)
            }
            val r = maxs.values.map{ if ( it == -1 ) 0 else it }.fold(1) { acc, elem -> if (elem == 0) acc else acc * elem }
            maxs = mutableMapOf()
            return@sumOf r
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("inputs/Day02.test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("inputs/Day02.input")
    part1(input).println()
    part2(input).println()
}

fun checkValues(sets: List<String>, possible: Map<String, Int>): Boolean {
    return sets.map { set ->
        val a = set.split(", ").map {
            val (count, color) = it.split(" ")

            count.toInt() <= possible.getOrDefault(color, -1)
        }.reduce { acc, b -> if (!b) false else acc }
        a
    }.reduce { acc, b -> if (!b) false else acc }
}
