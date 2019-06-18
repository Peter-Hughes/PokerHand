package poker.hand

class PokerHand(private val hand: List<Card>) {
    companion object {
        val allRanks: List<String> = listOf("A", "Q", "K", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2")
        fun build(line: String): PokerHand = PokerHand(line.split(" ").map { Card.build(it) })
    }

    override fun toString(): String = hand.joinToString(prefix = "[ ", postfix = " ]\t${name()}", separator = " ")

    private fun name(): String = when {
        isRoyalFlush() -> "Royal Flush"
        isStraightFlush() -> "Straight Flush"
        isFourOfAKind() -> "Four Of A Kind"
        isFullHouse() -> "Full House"
        isFlush() -> "Flush"
        isStraight() -> "Straight"
        isThreeOfAKind() -> "Three Of A Kind"
        isTwoPair() -> "Two Pair"
        isPair() -> "Pair"
        else -> "Highest Card"
    }

    private fun isRoyalFlush(): Boolean = isStraightFlush() && allCardsHigh()
    private fun isStraightFlush(): Boolean = isStraight() && isFlush()
    private fun isFullHouse(): Boolean = isThreeOfAKind() && isPair()
    private fun isFlush(): Boolean = hand.all { it.suit === hand[0].suit }

    private fun isStraight(): Boolean {
        val allRanksList = allRanks.joinToString("")
        val handRanks = ranksInDescendingOrder().joinToString("")

        return allRanksList.contains(handRanks)
    }

    private fun isTwoPair(): Boolean =
        hand.groupBy { it.rank }
            .filter { it.value.size == 2 }
            .size == 2

    private fun isFourOfAKind(): Boolean = hand.nOf(4)
    private fun isThreeOfAKind(): Boolean = hand.nOf(3)
    private fun isPair(): Boolean = hand.nOf(2)

    private infix fun List<Card>.nOf(x: Int) = this.asSequence().groupBy { it.rank }.any { it.value.size == x }

    private fun allCardsHigh(): Boolean = listOf("A", "Q", "K", "J", "10").all { r -> hand.any { it.rank == r } }

    private fun ranksInDescendingOrder(): List<String> {
        val comparator = Comparator<String> { a, b ->
            if (allRanks.indexOf(a) < allRanks.indexOf(b)) -1 else 1
        }

        return hand.map { it.rank }.sortedWith(comparator)
    }

}