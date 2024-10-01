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
        val winningLength = 5 // 승리 조건: 5개 연속
        val size = 5 // 보드 크기

        // 가로 승리 체크
        for (row in 0 until size) {
            for (col in 0 until size - winningLength + 1) {
                val cell = board[row * size + col]
                if (cell.isNotEmpty() && (0 until winningLength).all { board[row * size + col + it] == cell }) {
                    return cell
                }
            }
        }

        // 세로 승리 체크
        for (col in 0 until size) {
            for (row in 0 until size - winningLength + 1) {
                val cell = board[row * size + col]
                if (cell.isNotEmpty() && (0 until winningLength).all { board[(row + it) * size + col] == cell }) {
                    return cell
                }
            }
        }

        // 대각선 승리 체크 (왼쪽 위에서 오른쪽 아래)
        for (row in 0 until size - winningLength + 1) {
            for (col in 0 until size - winningLength + 1) {
                val cell = board[row * size + col]
                if (cell.isNotEmpty() && (0 until winningLength).all { board[(row + it) * size + (col + it)] == cell }) {
                    return cell
                }
            }
        }

        // 대각선 승리 체크 (오른쪽 위에서 왼쪽 아래)
        for (row in 0 until size - winningLength + 1) {
            for (col in winningLength - 1 until size) {
                val cell = board[row * size + col]
                if (cell.isNotEmpty() && (0 until winningLength).all { board[(row + it) * size + (col - it)] == cell }) {
                    return cell
                }
            }
        }

        return null // 승리자가 없으면 null 반환
    }


    private val history = mutableListOf<TicTacToeState>()

    fun saveState(state: TicTacToeState) {
        history.add(state)
    } // 자동으로 각 state 저장하게 끔 하는 ..

    fun goToState(index: Int) {
        if (index in history.indices) {
            _state.value = history[index-1]
        }
    }

}
