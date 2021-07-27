fun main() {
    val name = "Player"
    var healthPoints = 89
    val isBlessed = true
    val isImmortal = false

    val auraVisible = isBlessed && healthPoints > 50 || isImmortal
    val auraColor = if (auraVisible) "GREEN" else "NONE"
    println(auraColor)

    val status = if (healthPoints == 100) {
        "is in excellent condition!"
    } else if (healthPoints >= 90) {
        "has a few scratches."
    } else if (healthPoints >= 75) {
        if (isBlessed) {
            "has some minor wounds but is healing quite quickly!"
        } else {
            "has some minor wounds."
        }
    } else if (healthPoints >= 15) {
        "looks pretty hurt."
    } else {
        "is in awful condition!"
    }
    // Состояние игрока
    println("$name $status")
}