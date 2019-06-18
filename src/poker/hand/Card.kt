package poker.hand

data class Card(val suit: Suit, val rank: String) {
    companion object {
        fun build(input: String): Card = Card(Suit.valueOf(input.last().toString()), input.dropLast(1))
    }

    override fun toString(): String = "$rank$suit"
}