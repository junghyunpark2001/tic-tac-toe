package com.example.tictactoe

import TicTacToeState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class TicTacToeViewModel : ViewModel() {
    private val _state = MutableLiveData(TicTacToeState()) // 게임의 현재 상태, tictactoestate를 새롭게 만듦.
    val state: LiveData<TicTacToeState> = _state // 외부에서 관찰 가능한 LiveData

    fun onCellClicked(index: Int){
        val currentState = _state.value?: return

        // 게임 종료 혹은 이미 선택된 칸 클릭 -> 무시
        if(currentState.isGameOver || currentState.board[index].isNotEmpty()) return

        val newBoard = currentState.board.toMutableList()
        newBoard[index] = currentState.currentPlayer

        val winner = checkWinner(newBoard)

        _state.value = currentState.copy(
            board = newBoard,
            currentPlayer = if (currentState.currentPlayer == "X") "O" else "X",
            isGameOver = winner != null || newBoard.none { it.isEmpty() },
            winner = winner
        )
    }

    fun resetGame() {
        _state.value = TicTacToeState()
    }

    private fun checkWinner(board: List<String>): String? {
        val winningCombinations = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // 가로 승리
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // 세로 승리
            listOf(0, 4, 8), listOf(2, 4, 6) // 대각선 승리
        )
        for (combination in winningCombinations) {
            val (a, b, c) = combination
            if (board[a].isNotEmpty() && board[a] == board[b] && board[b] == board[c]) {
                return board[a]
            }
        }
        return null
    }

    private val history = mutableListOf<TicTacToeState>()

    fun saveState(state: TicTacToeState) {
        history.add(state)
        _state.value = state
    } // 자동으로 각 state 저장하게 끔 하는 ..

    fun goToState(index: Int) {
        if (index in history.indices) {
            _state.value = history[index]
        }
    }

}
