
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

fun String.getHandType2() : HandType {
    val counter = mutableMapOf<Char, Int>()
    this.map {
        counter[it] = counter.getOrDefault(it, 0) + 1
    }

    val counterWithoutJ = counter.filter { (k, _) -> k != 'J' }

    val hasJ = counter.contains('J')
    val jVal = counter.getOrDefault('J', -1)

    if (5 in counter.values ||
        (hasJ && 4 in counterWithoutJ.values && jVal == 1) ||
        (hasJ && 3 in counterWithoutJ.values && jVal == 2) ||
        (hasJ && 2 in counterWithoutJ.values && jVal == 3) ||
        (hasJ && 1 in counterWithoutJ.values && jVal == 4)
    )
        return HandType.FIVE_OF_KIND

    if (4 in counter.values ||
        (hasJ && 3 in counterWithoutJ.values && jVal == 1) ||
        (hasJ && 2 in counterWithoutJ.values && jVal == 2)
    )
        return HandType.FOUR_OF_KIND

    if (3 in counter.values && 2 in counter.values)
        return HandType.FULL_HOUSE

    if ((3 in counter.values) ||
        (hasJ && 2 in counterWithoutJ.values && jVal == 1) ||
        (hasJ && 1 in counterWithoutJ.values && jVal == 2)
    )
        return HandType.THREE_OF_KIND

    if (2 in counter.values || (hasJ && jVal == 1)) {
        if (counter.filter { (_, count) -> count == 2 }.size == 2)
            return HandType.TWO_PAIR
        return HandType.ONE_PAIR
    }

    return HandType.HIGH_CARD
}

data class Hand(
    val hand: String,
    val type: HandType,
    val bid: Long
) : Comparable<Hand> {

    constructor(hand: String, bid: Long) : this(hand, hand.getHandType(), bid)

    override fun compareTo(other: Hand): Int {
        if (this.type != other.type)
            return this.type.compareTo(other.type)

        hand.forEachIndexed { index, c ->
            val cValue = c.toCardValue()
            val oValue = other.hand[index].toCardValue()

            if (cValue != oValue)
                return cValue.compareTo(oValue)
        }

        return 0
    }
}

fun Char.toCardValue() : Int {
    return when(this) {
        'T' -> 10
        'J' -> 11
        'Q' -> 12
        'K' -> 13
        'A' -> 14
        else -> {
            if (this.isDigit())
                this.digitToInt()
            else
                -1
        }
    }
}

fun Char.toCardValue2() : Int {
    return when(this) {
        'J' -> 1
        else -> this.toCardValue()
    }
}

fun main() {
    fun part1(input: List<String>): Long {

        val hands = input.map { line ->
            val (hand, bid) = line.split(" ")
            Hand(hand, bid.toLong())
        }

        return hands.sorted().mapIndexed { idx, it ->
            (idx + 1) to it
        }.sumOf { (rank, h) ->
            rank * h.bid
        }
    }

    fun part2(input: List<String>): Long {
        val hands = input.map { line ->
            val (hand, bid) = line.split(" ")
            val handType = hand.getHandType2()
            Hand(hand, handType, bid.toLong())
        }

        return hands.sortedWith(Comparator { one: Hand, other: Hand ->
            if (one.type != other.type)
                return@Comparator one.type.compareTo(other.type)

            one.hand.forEachIndexed { index, c ->
                val cValue = c.toCardValue2()
                val oValue = other.hand[index].toCardValue2()

                if (cValue != oValue)
                    return@Comparator cValue.compareTo(oValue)
            }
            return@Comparator 0

        }).mapIndexed { idx, it ->
            (idx + 1) to it
        }.sumOf { (rank, h) ->
            rank * h.bid
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("inputs/Day07.test")
    check(part1(testInput) == 6440L)
    check(part2(testInput) == 5905L)

    val input = readInput("inputs/Day07.input")
    part1(input).println()
    part2(input).println()
}
