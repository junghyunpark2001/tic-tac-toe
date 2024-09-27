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
        recyclerView.layoutManager = GridLayoutManager(this, 3)
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
            adapter.updateBoard(state.board) // 보드를 업데이트 하는 부분. X, O 등이 입력됨. adapter를

            recordAdapter.updateBoard(state) // 서랍에서도 state에 따라서 board가 업데이트 되도록.

//            if (state.board != List(9) { "" })
//                recordAdapter.addItem(ListItem.AfterItem(RecordState(state.board))) // recordAdapter에 보드 데이터 할당
//            // 처음에 board가 막 초기화 되었을 때 입력되는 부분을 제외시켜줘야함.

            // 상태 업데이트는 전부 adapter 안으로 옮겼음. 결국은 viewModel과 recyclerview를 연결하려면 viewmodel을 adapter안에 정의하여 참조하는 작업이 필요..

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
                recordAdapter = RecordAdapter( mutableListOf(ListItem.StartItem("게임 시작")), viewModel)
                recyclerViewRecord.adapter = recordAdapter

            } // 근데 이걸 observe 안에 넣어도 상관없는건가요? 예제에서는 클릭 확인 같은 것은 밖에서 하는 것 같았는데
        }
    }
}
