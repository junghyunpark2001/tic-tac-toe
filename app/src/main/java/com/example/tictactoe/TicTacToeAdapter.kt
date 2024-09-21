package com.example.tictactoe

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
        notifyDataSetChanged()  // 전체 보드를 갱신
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cell, parent, false)
        return CellViewHolder(view)
    }

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {
        holder.bind(board[position], position)
        holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.light_yellow)) // 연노랑 색상으로 설정

    }

    override fun getItemCount() = board.size

    inner class CellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val button: Button = itemView.findViewById(R.id.cellButton)

        fun bind(cellValue: String, position: Int) {
            button.text = cellValue
            button.setOnClickListener {
                viewModel.onCellClicked(position)  // ViewModel에 클릭 전달
            }
        }
    }
}
