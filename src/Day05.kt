
data class IntervalMapping(val source: Long, val dest: Long, val size: Long) {

    fun inInterval(x : Long): Boolean = (source <= x && x < source + size)

    fun getMappingFor(x: Long): Long {
        if (!inInterval(x))
            return x

        return x - source + dest
    }
}

fun main() {
    fun createSeedList(line: String) : List<Long> {
        return line
            .split(" ")
            .drop(1)
            .map { it.toLong() }
            .fold(mutableListOf()) { acc, elem ->
                acc.add(elem)
                acc
            }
    }

    fun MutableList<IntervalMapping>.createMapping(line: String) {
        val (desStart, sourceStart, len) = line.split(" ").map { it.toLong() }
        this.add(IntervalMapping(sourceStart, desStart, len))
    }

    fun getMapping(input: List<String>): Pair<Int, List<IntervalMapping>> {
        val res = mutableListOf<IntervalMapping>()

        for (i in input.indices) {
            if (input[i].isEmpty()) {
                return i + 1 to res
            }
            res.createMapping(input[i])
        }
        return input.size to res
    }

    fun part1(input: List<String>): Long {

        var seedList = createSeedList(input[0])

        var i = 3
        while (i < input.size) {
            val (ended, mapping) = getMapping(input.subList(i, input.size))
            seedList = seedList.map { x ->
                mapping.forEach {
                    if (it.inInterval(x)) return@map it.getMappingFor(x)
                }
                x
            }
            i += ended + 1
        }

        return seedList.min()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("inputs/Day05.test")
    check(part1(testInput) == 35L)
    // check(part2(testInput) == 1)

    val input = readInput("inputs/Day05.input")
    part1(input).println()
    // part2(input).println()
}
