fun main() {

    fun countOptions(time: Long, record: Long) : Long {
        var count = 0L
        for (speed in 0 until time) {
            val remaining = time - speed
            val travelled = speed * remaining
            if (travelled > record) {
                count++
            }
        }
        return count
    }

    fun part1(input: List<String>): Long {

        val times = input[0].split(Regex(" +")).drop(1).map(String::toLong)
        val records = input[1].split(Regex(" +")).drop(1).map(String::toLong)
        var result = 1L
        for (i in times.indices) {
            result *= countOptions(times[i], records[i])
        }

        return result
    }

    fun part2(input: List<String>): Long {

        val time = input[0].replace(" ", "").split(":").drop(1).map { it.toLong() }
        val record = input[1].replace(" ", "").split(":").drop(1).map { it.toLong() }

        return countOptions(time[0], record[0])
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("inputs/Day06.test")
    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)

    val input = readInput("inputs/Day06.input")
    part1(input).println()
    part2(input).println()
}
