package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TicTacToeViewModel
    private lateinit var adapter: TicTacToeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(TicTacToeViewModel::class.java)
        adapter = TicTacToeAdapter(viewModel)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter

        // 상태 변화 관찰
        viewModel.state.observe(this) { state ->
            adapter.updateBoard(state.board)

            val statusTextView = findViewById<TextView>(R.id.statusTextView)
            statusTextView.text = when {
                state.isGameOver && state.winner != null -> "${state.winner} 승리!"
                state.isGameOver -> "무승부"
                else -> "${state.currentPlayer}의 차례입니다"
            }

            val resetButton = findViewById<Button>(R.id.resetButton)
            resetButton.text = if (state.isGameOver) "한판 더" else "초기화"
            resetButton.setOnClickListener {
                viewModel.resetGame()
            }
        }
    }
}
