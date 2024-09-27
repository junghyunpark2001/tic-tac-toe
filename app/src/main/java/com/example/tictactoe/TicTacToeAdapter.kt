package com.example.tictactoe

import TicTacToeState
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class TicTacToeAdapter(
    private val viewModel: TicTacToeViewModel
) : RecyclerView.Adapter<TicTacToeAdapter.CellViewHolder>() {

    private var board: List<String> = listOf()

    fun updateBoard(newBoard: List<String>) {
        board = newBoard
        notifyDataSetChanged()  // 전체 보드를 갱신, 리스트의 크기와 아이템이 변경되는 경우 사용하는 함수. 즉, 동적 데이터 뷰 업데이트.
    }

    // 어떻게 업데이트 하는지는 아래 함수들로.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cell, parent, false)
        return CellViewHolder(view)
    }

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {
        holder.bind(board[position], position) // 여기서 클릭이 전달되고, text가 쓰임.
        holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.light_yellow)) // 연노랑 색상으로 설정

    }

    override fun getItemCount() = board.size

    inner class CellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val button: Button = itemView.findViewById(R.id.cellButton)

        fun bind(cellValue: String, position: Int) { //bind 함수는 bindviewholder에서 쓰임
            button.text = cellValue // text가 적히는 부분
            button.setOnClickListener {
                viewModel.onCellClicked(position)  // ViewModel에 클릭 전달
                val currentState = viewModel.state.value!!
                viewModel.saveState(currentState)
            }
        }
    }
}
