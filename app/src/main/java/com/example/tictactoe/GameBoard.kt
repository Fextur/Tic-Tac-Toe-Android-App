package com.example.tictactoe

class GameBoard {
    private val board = Array(3) { Array(3) { GameCell()} }

    fun makeMove(row: Int, col: Int, player: Char): Boolean {
        if(board[row][col].isEmpty()) {
            board[row][col].player = player
            return true;
        }
        return false;
    }

    fun checkWin(): Char? {
        for(i in board.indices) {
            if(!board[i][0].isEmpty() && board[i].all { it.player == board[i][0].player })
                return board[i][0].player
            if(!board[0][i].isEmpty() && board.indices.all { board[it][i].player == board[0][i].player })
                return board[0][i].player
        }
        if(!board[0][0].isEmpty() && board.indices.all { board[it][it].player == board[0][0].player })
            return board[0][0].player
        if(!board[0][board.size-1].isEmpty() && board.indices.all { board[it][board.size - it -1 ].player == board[0][board.size-1].player })
            return board[0][board.size-1].player

        return null;
    }
}