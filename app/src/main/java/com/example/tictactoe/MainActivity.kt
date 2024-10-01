package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
        recyclerView.layoutManager = GridLayoutManager(this, 5)
        recyclerView.adapter = adapter


        // 서랍 여는 버튼 설정
        val openDrawerButton = findViewById<Button>(R.id.drawerButton)
        val drawerlayout = findViewById<DrawerLayout>(R.id.drawerLayout)

        openDrawerButton.setOnClickListener {
            if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
                drawerlayout.closeDrawer(GravityCompat.START)
            } else {
                drawerlayout.openDrawer(GravityCompat.START)
            }
        }

        // 서랍 연 것에 대한 recyclerview
        val recyclerViewRecord = findViewById<RecyclerView>(R.id.navigationRecyclerView)
        recyclerViewRecord.layoutManager = LinearLayoutManager(this) // 서랍 리니어로 설정
        var recordAdapter = RecordAdapter( mutableListOf(ListItem.StartItem("게임 시작")), viewModel) // 이런식으로 RecordAdapter라는 어뎁터의 클래스를 생성해줌.
        recyclerViewRecord.adapter = recordAdapter // 이렇게 해야 adapter 계속 업데이트 가능



// 상태 변화 관찰
        viewModel.state.observe(this) { state ->
            adapter.updateBoard(state.board) // 보드를 업데이트 하는 부분

            val statusTextView = findViewById<TextView>(R.id.statusTextView)
            statusTextView.text = when {
                state.isGameOver && state.winner != null -> "${state.winner} 승리!"
                state.isGameOver -> "무승부"
                else -> "${state.currentPlayer}의 차례입니다"
            }

            val resetButton = findViewById<Button>(R.id.resetButton)
            resetButton.text = if (state.isGameOver) "한판 더" else "초기화"

            // resetButton 클릭 리스너를 observe 외부로 이동
            resetButton.setOnClickListener {
                // recordAdapter 업데이트 로직
                recordAdapter.updateBoard(state)
                viewModel.resetGame()
            }



        }


    }
}
