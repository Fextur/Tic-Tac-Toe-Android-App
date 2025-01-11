package com.example.tictactoe

data class GameCell(var player: Char? = null) {
    fun isEmpty() = player == null
}