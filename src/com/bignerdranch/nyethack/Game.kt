package com.bignerdranch.nyethack

import java.io.File

fun main() {

    val player = Player(_name = "Madrigal",
        healthPoints = 100,
        isBlessed = true,
        isImmortal = false)
    player.castFireball(9)

    printPlayerStatus(player)
}

private fun printPlayerStatus(player: Player) {
    println("(Aura: ${player.auraColor()}) " + "(Blessed: ${if (player.isBlessed) "YES" else "NO"})")
    println("${player.name} ${player.formatHealthStatus()}")
}

class Player (_name: String,
              var healthPoints: Int = 100,
              val isBlessed: Boolean,
              private val isImmortal: Boolean){

    val hometown = selectHometown()

    var name = _name
        get() = "${field.lowercase()} of $hometown"
        private set(value) {
            field = value.trim()
        }

    init {
        require(healthPoints > 0) { "healthPoints must be greater than zero." }
        require(name.isNotBlank()) { "Player must have a name." }
    }

    constructor(name: String) : this(name,
        isBlessed = true,
        isImmortal = false) {
        if (name.lowercase() == "kar") healthPoints = 40}

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

    private fun selectHometown() = File("data/towns.txt")
        .readText()
        .split("\n")
        .shuffled()
        .first()
}