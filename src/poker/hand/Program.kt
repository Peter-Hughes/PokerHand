package poker.hand


import java.io.File

fun main() {
    fun readHands(fileName: String): List<PokerHand> {
        val file = File(fileName)
        return file.readLines()
            .filter(::checkHand)
            .map { PokerHand.build(it) }
    }

    val hands = readHands("input/pokerHands.txt")
    printHands(hands)
}

fun checkHand(line: String): Boolean {
    val regexTxt = "^((([1-9AQJK]|10)[HDSC]) ){4}(([1-9AQJK]|10)[HDSC])\$"
    val pokerRegex = Regex(regexTxt)
    val result = line.matches(pokerRegex)

    if (!result) println("Ignoring: \"$line\"")

    return result
}

fun printHands(hands: List<PokerHand>) = hands.forEach(::println)