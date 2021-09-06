package com.bignerdranch.nyethack

fun main() {

    val player = Player()
    player.castFireball(9)

    printPlayerStatus(player)
}

private fun printPlayerStatus(player: Player) {
    println("(Aura: ${player.auraColor()}) " + "(Blessed: ${if (player.isBlessed) "YES" else "NO"})")
    println("${player.name} ${player.formatHealthStatus()}")
}

class Player{
    var name = "John"
        get() = field.lowercase()
        private set(value){
            field = value.trim()
        }

    var healthPoints = 89
    val isBlessed = true
    private val isImmortal = false

    fun auraColor(): String {
        val auraVisible = isBlessed && healthPoints > 50 || isImmortal
        return if (auraVisible) "GREEN" else "NONE"
    }

    fun formatHealthStatus() = when (healthPoints) {
            100 -> "is in excellent condition!"
            in 90..99 -> "has a few scratches."
            in 75..89 -> if (isBlessed) {
                "has some minor wounds, but is healing quite quickly!"
            } else {
                "has some minor wounds."
            }
            in 15..74 -> "looks pretty hurt."
            else -> "is in awful condition!"
        }

    fun castFireball(numFireballs: Int = 2) =
        println("A glass of Fireball springs into existence. (x$numFireballs)")
}