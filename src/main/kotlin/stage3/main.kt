package stage3

fun main() {
    println("Input the number of cards:")
    val noOfCards = readLine()!!.toInt()

    val cards = buildCardDeck(noOfCards)

    testUser(cards)
}

fun buildCardDeck(noOfCards: Int): List<Card> {
    val cards = emptyList<Card>().toMutableList()

    for (i in 1..noOfCards) {
        println("Card #$i")
        val term = readLine()!!
        println("The definition for card #$i")
        val definition = readLine()!!
        cards.add(Card(term, definition))
    }
    return cards
}

fun testUser(cards: List<Card>) {
    for (card in cards) {
        println("""Print the definition of "${card.term}":""")
        val answer = readLine()!!
        if (answer == card.definition) {
            println("Correct!")
        } else {
            println("""Wrong. The right answer is "${card.definition}".""")
        }
    }
}

data class Card(val term: String, val definition: String)