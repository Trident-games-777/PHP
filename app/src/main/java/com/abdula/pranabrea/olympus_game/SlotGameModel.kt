package com.abdula.pranabrea.olympus_game

sealed class SlotGameModel {
    data class Idle(val slots: List<Int>) : SlotGameModel()
    data class Spinning(val slots: List<Int>) : SlotGameModel()
    data class Result(val slots: List<Int>, val win: Boolean) : SlotGameModel()
}
