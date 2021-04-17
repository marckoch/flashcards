package stage2

fun main() {
    val (_, definition, answer) = List(3) { readLine()!! }

    if (definition == answer) {
        println("Your answer is right!")
    } else {
        println("Your answer is wrong!")
    }
}