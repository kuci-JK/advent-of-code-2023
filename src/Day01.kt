fun main() {
    fun part1(input: List<String>): Int {

        val res = input.map {
            return@map it.filter { c -> c.isDigit() }
        }.map {"${it.first()}${it.last()}"
        }.sumOf { it.toInt() }

        return res
    }

    fun part2(input: List<String>): Int {
        val conv = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9",
        )

        val res = input.stream().mapToInt {
            // replacing
            var result = ""

            var i = 0
            while (i < it.length) {
                if (it[i].isDigit()) {
                    result += it[i]
                }

                for ( key in conv.keys ) {
                    if (it.startsWith(key, i)) {
                        result += conv[key]
                        break
                    }
                }
                i++
            }
            "${result.first()}${result.last()}".toInt()
        }.sum();

        return res
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("inputs/Day01.test")
    check(part1(testInput) == 142)

    val input = readInput("inputs/Day01.input")
    part1(input).println()

    val testInput2 = readInput("inputs/Day01.test2")
    check(part2(testInput2) == 281)

    part2(input).println()
}
