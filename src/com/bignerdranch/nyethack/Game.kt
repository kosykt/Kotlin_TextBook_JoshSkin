package com.bignerdranch.nyethack

import java.io.File

fun main() {
    Game.play()
}

object Game {
    val player = Player("Madrigal")
    var currentRoom: Room = TownSquare()

    private var worldMap = listOf(
        listOf(currentRoom, Room("Tavern"), Room("Back Room")),
        listOf(Room("Long Corridor"), Room("Generic Room")))

    init {
        println("Welcome, adventurer.")
        player.castFireball(9)
    }

    fun play() {
        while (true) {
            println(currentRoom.description())
            println(currentRoom.load())

            printPlayerStatus(player)

            print("> Enter your command: ")
            println(GameInput(readLine()).processCommand())
        }
    }

    private fun printPlayerStatus(player: Player) {
        println("(Aura: ${player.auraColor()}) " + "(Blessed: ${if (player.isBlessed) "YES" else "NO"})")
        println("${player.name} ${player.formatHealthStatus()}")
    }

    private class GameInput(arg: String?) {
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1, { "" })

        fun processCommand() = when (command.lowercase()) {
            "move" -> move(argument)
            else -> commandNotFound()
        }

        private fun commandNotFound() = "I'm not quite sure what you're trying to do!"
    }

    private fun move(directionInput: String) =
        try {
            val direction = Direction.valueOf(directionInput.uppercase())
            val newPosition = direction.updateCoordinate(player.currentPosition)
            if (!newPosition.isInBounds) {
                throw IllegalStateException("$direction is out of bounds.")
            }
            val newRoom = worldMap[newPosition.y][newPosition.x]
            player.currentPosition = newPosition
            currentRoom = newRoom
            "OK, you move $direction to the ${newRoom.name}.\n${newRoom.load()}"
        } catch (e: Exception) {
            "Invalid direction: $directionInput."
        }
}

class Player(
    _name: String,
    var healthPoints: Int = 100,
    val isBlessed: Boolean,
    private val isImmortal: Boolean
) {

    var name = _name
        get() = "${field.lowercase()} of $hometown"
        private set(value) {
            field = value.trim()
        }

    val hometown by lazy { selectHometown() }
    var currentPosition = Coordinate(0, 0)

    init {
        require(healthPoints > 0) { "healthPoints must be greater than zero." }
        require(name.isNotBlank()) { "Player must have a name." }
    }

    constructor(name: String) : this(
        name,
        isBlessed = true,
        isImmortal = false
    ) {
        if (name.lowercase() == "kar") healthPoints = 40
    }

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
