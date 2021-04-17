package stage4

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

        var term: String
        while (true) {
            term = readLine()!!
            if (!isDuplicateTerm(term, cards))
                break

            println("""The term "$term" already exists. Try again:""")
        }

        println("The definition for card #$i")
        var definition: String
        while (true) {
            definition = readLine()!!
            if (!isDuplicateDefinition(definition, cards))
                break

            println("""The definition "$definition" already exists. Try again:""")
        }

        cards.add(Card(term, definition))
    }
    return cards
}

fun isDuplicateTerm(term: String, cards: List<Card>): Boolean {
    return cards.map { it.term }.contains(term)
}

fun isDuplicateDefinition(definition: String, cards: List<Card>): Boolean {
    return findCardByDefinition(definition, cards) != null
}

fun findCardByDefinition(definition: String, cards: List<Card>): Card? {
    return cards.find { it.definition == definition }
}

fun testUser(cards: List<Card>) {
    for (card in cards) {
        val (term, definition) = card

        println("""Print the definition of "$term":""")
        val answer = readLine()!!

        if (answer == definition) {
            println("Correct!")
        } else {
            val otherCard = findCardByDefinition(answer, cards)
            val response = """Wrong. The right answer is "$definition""""
            if (otherCard != null) {
                println("""$response, but your definition is correct for "${otherCard.term}".""")
            } else
                println("""$response.""")
        }
    }
}

data class Card(val term: String, val definition: String)