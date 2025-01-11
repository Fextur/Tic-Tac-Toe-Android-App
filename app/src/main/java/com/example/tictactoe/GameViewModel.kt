package com.example.tictactoe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val _board = GameBoard();
    private val _currentPlayer = MutableLiveData('X')
    private val _winner = MutableLiveData<Char?>(null)
    private val _isDraw = MutableLiveData(false)

    val currentPlayer: LiveData<Char> get() = _currentPlayer
    val winner: LiveData<Char?> get() = _winner
    val isDraw: LiveData<Boolean> get() = _isDraw

    fun makeMove(row: Int, col: Int, updateUI: (Char) -> Unit): Boolean {
       if(!hasGameFinished() &&_board.makeMove(row, col, _currentPlayer.value!!)) {
           updateUI(_currentPlayer.value!!)
           _winner.value = _board.checkWin()
           _isDraw.value = _board.isDraw()

           if(!hasGameFinished()) {
               _currentPlayer.value = if (_currentPlayer.value == 'X') 'O' else 'X'
           }
           return true;
       }
        return false;
    }

    private fun hasGameFinished(): Boolean {
        return _winner.value != null || _isDraw.value == true
    }

    fun resetGame() {
        _board.reset()
        _currentPlayer.value = 'X'
        _winner.value = null
        _isDraw.value = false
    }
}