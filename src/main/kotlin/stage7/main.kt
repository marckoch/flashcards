package stage7


import java.io.File
import java.io.FileNotFoundException

fun main(args: Array<String>) {
    val (import, export) = parseArgs(args)
    Flashcards().go(import, export)
}

fun parseArgs(args: Array<String>): Pair<String?, String?> {
    if (args.size !in listOf(2, 4)) {
        return Pair(null, null)
    }

    var importFilename = ""
    var exportFilename = ""

    for (arg in args.asList().chunked(2)) {
        val paramKey = arg[0]
        val paramValue = arg[1]

        when (paramKey) {
            "-import" -> importFilename = paramValue
            "-export" -> exportFilename = paramValue
        }
    }

    return Pair(importFilename, exportFilename)
}

class Flashcards {
    private val cards = emptyList<Card>().toMutableList()
    private val log = mutableListOf<String>()

    fun go(importFilename: String?, exportFilename: String?) {

        if (importFilename != null) {
            import(importFilename)
        }

        while (true) {
            println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):")
            val action = readLine()!!
            when (action) {
                "add" -> addCard()
                "remove" -> removeCard()
                "export" -> export()
                "import" -> import()
                "ask" -> testUser()
                "log" -> log()
                "hardest card" -> hardestCard()
                "reset stats" -> reset()
                "exit" -> break
                else -> println("Sorry. I didn't get that!")
            }
        }

        if (exportFilename != null) {
            export(exportFilename)
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
        if (isDuplicateDefinition(definition)) {
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

        export(filename)
    }

    private fun export(filename: String) {
        File(filename).writeText(cards.joinToString("\n") { it.toExportFormat() })
        println("${cards.size} cards have been saved.")
    }

    private fun import() {
        println("File name:")
        val filename = readLine()!!

        import(filename)
    }

    private fun import(filename: String) {
        try {
            val lines = File(filename).readLines()
            val loadedCards = lines.map { s -> Card.fromExportFormat(s) }
            for (newCard in loadedCards) {
                cards.find { card -> card.term == newCard.term }.let { card -> cards.remove(card) }
                cards.add(newCard)
            }
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

        repeat(noOfCards) {
            val card = cards.random()
            val (term, definition) = card

            println("""Print the definition of "$term":""")
            val answer = readLine()!!

            if (answer == definition) {
                card.countAnsweredCorrect++
                println("Correct!")
            } else {
                card.countAnsweredWrong++
                val otherCard = findCardByDefinition(answer)
                val response = """Wrong. The right answer is "$definition""""
                if (otherCard != null) {
                    println("""$response, but your definition is correct for "${otherCard.term}".""")
                } else
                    println("""$response.""")
            }
        }
    }

    private fun hardestCard() {
        val hardest = cards
            .groupBy { card -> card.countAnsweredWrong }
            .maxByOrNull { entry -> entry.key }

        if (hardest == null || hardest.key == 0) {
            println("There are no cards with errors.")
        } else if (hardest.value.size == 1) {
            println(
                """The hardest card is "${hardest.value[0].term}". 
                |You have ${hardest.key} errors answering it.""".trimMargin()
            )
        } else {
            println("The hardest cards are " +
                    hardest.value.joinToString(", ") { card -> """"${card.term}""""} +
                    ". You have ${hardest.key} errors answering them.")
        }
    }

    private fun reset() {
        cards.forEach { card -> card.resetStats() }
        println("Card statistics have been reset.")
    }

    private fun log() {
        println("File name:")
        val logfile = readLine()!!
        File(logfile).writeText(log.joinToString("\n"))
        println("The log has been saved.")
    }

    private fun println(s: String) {
        log.add(s)
        kotlin.io.println(s)
    }

    private fun readLine(): String? {
        val s = kotlin.io.readLine()!!
        log.add(s)
        return s
    }
}

data class Card(
    val term: String,
    val definition: String,
    var countAnsweredCorrect: Int = 0,
    var countAnsweredWrong: Int = 0
) {
    fun toExportFormat(): String {
        return "$term:$definition:$countAnsweredCorrect:$countAnsweredWrong"
    }

    fun resetStats() {
        countAnsweredWrong = 0
        countAnsweredCorrect = 0
    }

    companion object {
        fun fromExportFormat(s: String): Card {
            val (term, definition, countAnsweredCorrect, countAnsweredWrong) = s.split(":").map { it.trim() }
            return Card(term, definition, countAnsweredCorrect.toInt(), countAnsweredWrong.toInt())
        }
    }
}