package stage5

import java.io.File
import java.io.FileNotFoundException

fun main() {
    Flashcards().go()
}

class Flashcards {
    private val cards = emptyList<Card>().toMutableList()

    fun go() {
        while (true) {
            println("Input the action (add, remove, import, export ask, exit):")
            val action = readLine()!!
            when (action) {
                "add" -> addCard()
                "remove" -> removeCard()
                "export" -> export()
                "import" -> import()
                "ask" -> testUser()
                "exit" -> break
                else -> println("Sorry. I didn't get that!")
            }
        }
        println("Bye bye!")
    }

    private fun addCard() {
        println("The card:")

        val term = readLine()!!
        if (isDuplicateTerm(term)) {
            println("""The card "$term" already exists.""")
            return
        }

        println("The definition of the card:")
        val definition = readLine()!!
        if (isDuplicateDefinition(definition)){
            println("""The definition "$definition" already exists.""")
            return
        }

        cards.add(Card(term, definition))
        println("""The pair ("$term":"$definition") has been added""")
    }

    private fun removeCard() {
        println("Which card?")
        val term = readLine()!!

        val card = cards.find { it.term == term }
        if (card != null) {
            cards.remove(card)
            println("The card has been removed.")
        } else {
            println("""Can't remove "$term": there is no such card.""")
        }
    }

    private fun export() {
        println("File name:")
        val filename = readLine()!!
        File(filename).writeText(cards.map { it.toExportFormat() }.joinToString("\n"))
        println("${cards.size} cards have been saved.")
    }

    private fun import() {
        println("File name:")
        val filename = readLine()!!

        try {
            val lines = File(filename).readLines()
            cards.addAll(lines.map { s -> Card.fromImportFormat(s) })
            println("${lines.size} cards have been loaded.")
        } catch (e: FileNotFoundException) {
            println("File not found.")
        }
    }

    private fun isDuplicateTerm(term: String): Boolean {
        return cards.map { it.term }.contains(term)
    }

    private fun isDuplicateDefinition(definition: String): Boolean {
        return findCardByDefinition(definition) != null
    }

    private fun findCardByDefinition(definition: String): Card? {
        return cards.find { it.definition == definition }
    }

    private fun testUser() {
        println("How many times to ask?")
        val noOfCards = readLine()!!.toInt()

        repeat (noOfCards) {
            val (term, definition) = cards.random()

            println("""Print the definition of "$term":""")
            val answer = readLine()!!

            if (answer == definition) {
                println("Correct!")
            } else {
                val otherCard = findCardByDefinition(answer)
                val response = """Wrong. The right answer is "$definition""""
                if (otherCard != null) {
                    println("""$response, but your definition is correct for "${otherCard.term}".""")
                } else
                    println("""$response.""")
            }
        }
    }
}

data class Card(val term: String, val definition: String) {
    fun toExportFormat(): List<String> {
        return listOf(term, definition)
    }

    companion object {
        fun fromImportFormat(s: String): Card {
            val term = s.replace("[", "").substringBefore(",")
            val definition = s.replace("]", "").substringAfter(", ")
            return Card(term, definition)
        }
    }
}