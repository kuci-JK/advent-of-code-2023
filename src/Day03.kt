fun main() {

    fun Char.isSpecial() = !this.isDigit() && this != '.'

    fun hasSpecialAround(lineIdx: Int, from: Int, to: Int, input: List<String>): Boolean {
        if ((from > 0 && input[lineIdx][from - 1].isSpecial()) ||
            (to + 1 < input[lineIdx].length && input[lineIdx][to + 1].isSpecial())) {
            return true
        }


        for (i in from - 1 .. to + 1) {
            if (i < 0 || i >= input[lineIdx].length)
                continue

            if ((lineIdx > 0 && input[lineIdx - 1][i].isSpecial()) ||
                (lineIdx + 1 < input.size && input[lineIdx + 1][i].isSpecial())) {

                return true

            }
        }
        return false
    }

    fun part1(input: List<String>): Int {

        val numbers = input.mapIndexed { lineIdx, line ->
            var res = 0
            var chIdx = 0;
            while (chIdx < line.length) {
                if ( line[chIdx].isDigit() ) {
                    var numEnd = chIdx;
                    while (numEnd + 1 < line.length && line[numEnd + 1].isDigit()) {
                        numEnd++
                    }

                    if (hasSpecialAround(lineIdx, chIdx, numEnd, input)) {
                        res += line.substring(chIdx, numEnd + 1).toInt()
                    }
                    chIdx = numEnd

                }
                chIdx++
            }
            res
        }

        return numbers.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("inputs/Day03.test")
    check(part1(testInput) == 4361)
    // check(part2(testInput) == 1)

    val input = readInput("inputs/Day03.input")
    part1(input).println()
    // part2(input).println()
}
