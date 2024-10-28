package com.example.strike

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TicTacToeViewModel: ViewModel() {
    private val _state = mutableStateOf(TicTicToeState())
    val state: State<TicTicToeState> = _state

    fun setButton(buttonId: Int){
        if (_state.value.winner == null) {
            if (_state.value.buttonValues[buttonId] == "-"){
                val button = _state.value.buttonValues.copyOf()
                if (_state.value.isXTurn) {
                    button[buttonId] = "X"
                } else {
                    button[buttonId] = "O"
                }
                _state.value = _state.value.copy(
                    buttonValues = button,
                    isXTurn = ! _state.value.isXTurn
                )
            }
        }
        isGameOver()
    }

    private fun rowHasWinner(rowId: Int): Boolean {
        val third = (3*rowId) - 1
        val second = third - 1
        val first = second - 1

        return compareXO(first, second, third)
    }

    private fun columnHasWinner(columnId: Int): Boolean {
        val first = columnId - 1
        val second = first + 3
        val third = first + 6

        return compareXO(first, second, third)
    }

    private fun firstDiagonal(): Boolean {
        return compareXO(0, 4, 8)
    }

    private fun secondDiagonal(): Boolean {
        return compareXO(2, 4, 6)
    }
    private fun compareXO(first: Int, second: Int, third: Int): Boolean {
        val firstTwoMatch = _state.value.buttonValues[first] == _state.value.buttonValues[second]
        val secondTwoMatch = _state.value.buttonValues[second] == _state.value.buttonValues[third]
        val allMatch = firstTwoMatch && secondTwoMatch
        return if (_state.value.buttonValues[first] == "-") {
            false
        }else if(allMatch){
            val winnerButtons = _state.value.buttonWinners.copyOf()
            winnerButtons[first] = true
            winnerButtons[second] = true
            winnerButtons[third] = true
            _state.value = _state.value.copy(buttonWinners = winnerButtons)
            true
        } else {
            false
        }
    }

    private fun isGameOver(): Boolean {
        if (rowHasWinner(1) || rowHasWinner(2) || rowHasWinner(3)) {
            return true
        } else if (columnHasWinner(1) || columnHasWinner(2) || columnHasWinner(3)){
            return true
        } else if (firstDiagonal() || secondDiagonal()) {
            return true
        }
        return false
    }

    fun resetBoard() {
        _state.value = TicTicToeState()
    }
}