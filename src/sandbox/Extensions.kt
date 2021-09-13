package sandbox

fun String.addEnthusiasm(amount: Int = 1) = this + "!".repeat(amount)

val String.numVowels
    get() = count { "aeiouy".contains(it) }

fun <T> T.easyPrint(): T{
    println(this)
    return this
}

fun main() {
    "Madrigal has left the building".easyPrint().addEnthusiasm().easyPrint()

    "How many vowels?".numVowels.easyPrint()

}