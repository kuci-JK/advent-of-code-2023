
enum class HandType {
    HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_KIND, FULL_HOUSE, FOUR_OF_KIND, FIVE_OF_KIND
}

fun String.getHandType() : HandType {
    val counter = mutableMapOf<Char, Int>()
    this.map {
        counter[it] = counter.getOrDefault(it, 0) + 1
    }

    if (5 in counter.values)
        return HandType.FIVE_OF_KIND

    if (4 in counter.values)
        return HandType.FOUR_OF_KIND

    if (3 in counter.values && 2 in counter.values)
        return HandType.FULL_HOUSE

    if (3 in counter.values)
        return HandType.THREE_OF_KIND

    if (2 in counter.values) {
        if (counter.filter { (_, count) -> count == 2 }.size == 2)
            return HandType.TWO_PAIR
        return HandType.ONE_PAIR
    }

    return HandType.HIGH_CARD
}

data class Hand(
    private val hand: String,
    private val type: HandType,
    private val bid: Long
) {
    var rank: Long = -1

    constructor(hand: String, bid: Long) : this(hand, hand.getHandType(), bid)

}

fun main() {
    fun part1(input: List<String>): Int {

        val hands = input.map { line ->
            val (hand, bid) = line.split(" ")
            Hand(hand, bid.toLong())
        }

        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("inputs/Day07.test")
    check(part1(testInput) == 1)
    // check(part2(testInput) == 1)

    val input = readInput("inputs/Day07.input")
    part1(input).println()
    // part2(input).println()
}
