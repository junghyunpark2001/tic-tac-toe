package com.example.tictactoe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import TicTacToeState
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager

data class RecordState( // 서랍의 9칸 + n턴 + 되돌아가기 버튼에 대한 data class.
    val board: List<String> = List(25) { "" },  // 9개의 칸을 빈 문자열로 초기화
    val turn: Int = 0,  // 현재 플레이어는 X로 시작
    val button: String = "되돌아가기"
)

sealed class ListItem {
    data class StartItem(val text : String) : ListItem() // 처음 시작 버튼의 타입을 이걸로. itemview위해 type으로 설정. // data class라 매개변수 식으로 속성들을 전부 정의.

    data class AfterItem(val state : RecordState) : ListItem() // data class를 input으로 하는.

    data class EndItem(val state : RecordState , val text : String) : ListItem() // 마지막 승리 띄우기 위해.
}

class RecordAdapter (private var items: MutableList<ListItem>, private val viewModel:TicTacToeViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    companion object {
        const val TYPE_START = 0
        const val TYPE_AFTER = 1
        const val TYPE_END = 2
    }
    // 처음 '게임 시작' 버튼과 나머지 구분.

    override fun getItemViewType(position: Int): Int { // position은 자동으로 받아들이는 매개변수
        return when (items[position]) {
            is ListItem.StartItem -> TYPE_START
            is ListItem.AfterItem -> TYPE_AFTER
            is ListItem.EndItem -> TYPE_END
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drawer, parent, false)

        return when (viewType) {
            TYPE_START -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_start, parent, false) // item_start라는 xml을 view라는 변수에 저장
                StartViewHolder(view) // 그리고 보여준다. 이건 아래의 ViewHolder라는 또 하나의 클래스(ViewHolder)를 이용.
            }

            TYPE_AFTER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_after, parent, false) // item_start라는 xml을 view라는 변수에 저장
                AfterViewHolder(view, viewModel)
            }

            TYPE_END -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_end, parent, false) // item_start라는 xml을 view라는 변수에 저장
                EndViewHolder(view, viewModel)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }


    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) { // 어쨌든 create 된 viewholder 타입으로 받아지는 듯함. 데이터 binding이 이루어지는. ex) 텍스트 바꾸기
        when (holder) {
            is StartViewHolder -> holder.bind((items[position] as ListItem.StartItem).text) // .text는 처음에 받은 매개변수
            is AfterViewHolder -> {

                holder.bind("$position 턴")
                holder.bind_box((items[position] as ListItem.AfterItem).state.board, position)

                // n번째 턴으로 바꾸기.
            }
            is EndViewHolder -> {
                val value = viewModel.state.value
                holder.bind("$position 턴")
                holder.bind_box((items[position] as ListItem.EndItem).state.board, value?.isGameOver, value?.winner, position)
            }
        }
    }

    class StartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView =
            itemView.findViewById(R.id.textView) // item_start에서 textView라는 아이디 찾아서 가져오는 것.

        fun bind(text: String) {
            textView.text =
                text // item_start.xml의 textView id 가진 box의 text를 바꿀 수 있는 함수다. MainActivity에서 Adapter 인스턴스 정의할때 설정한 "ListItem.StartItem.text" 매개변수를 가지고.
        }
    }

    inner class AfterViewHolder(itemView: View, private val viewModel: TicTacToeViewModel) :
        RecyclerView.ViewHolder(itemView) {
        private val turn: TextView = itemView.findViewById(R.id.turn)

        private val button: TextView = itemView.findViewById(R.id.back)

        fun bind(text: String) {
            turn.text = text
        }

        fun bind_box(box: List<String>, position: Int) { // box의 글자 업데이트
            itemView.findViewById<TextView>(R.id.box_1).text = box[0]
            itemView.findViewById<TextView>(R.id.box_2).text = box[1]
            itemView.findViewById<TextView>(R.id.box_3).text = box[2]
            itemView.findViewById<TextView>(R.id.box_4).text = box[3]
            itemView.findViewById<TextView>(R.id.box_5).text = box[4]
            itemView.findViewById<TextView>(R.id.box_6).text = box[5]
            itemView.findViewById<TextView>(R.id.box_7).text = box[6]
            itemView.findViewById<TextView>(R.id.box_8).text = box[7]
            itemView.findViewById<TextView>(R.id.box_9).text = box[8]
            itemView.findViewById<TextView>(R.id.box_10).text = box[9]
            itemView.findViewById<TextView>(R.id.box_11).text = box[10]
            itemView.findViewById<TextView>(R.id.box_12).text = box[11]
            itemView.findViewById<TextView>(R.id.box_13).text = box[12]
            itemView.findViewById<TextView>(R.id.box_14).text = box[13]
            itemView.findViewById<TextView>(R.id.box_15).text = box[14]
            itemView.findViewById<TextView>(R.id.box_16).text = box[15]
            itemView.findViewById<TextView>(R.id.box_17).text = box[16]
            itemView.findViewById<TextView>(R.id.box_18).text = box[17]
            itemView.findViewById<TextView>(R.id.box_19).text = box[18]
            itemView.findViewById<TextView>(R.id.box_20).text = box[19]
            itemView.findViewById<TextView>(R.id.box_21).text = box[20]
            itemView.findViewById<TextView>(R.id.box_22).text = box[21]
            itemView.findViewById<TextView>(R.id.box_23).text = box[22]
            itemView.findViewById<TextView>(R.id.box_24).text = box[23]
            itemView.findViewById<TextView>(R.id.box_25).text = box[24]



            button.setOnClickListener{
                viewModel.goToState(position)
                updateItems(goToState(position))
            }

        }

    }

    inner class EndViewHolder(itemView: View, private val viewModel: TicTacToeViewModel) :
        RecyclerView.ViewHolder(itemView) {
        private val turn: TextView = itemView.findViewById(R.id.turn)

        private val button: TextView = itemView.findViewById(R.id.back)

        fun bind(text: String) {
            turn.text = text
        }

        fun bind_box(box: List<String>, isGameOver: Boolean?, winner: String?, position: Int) { // box의 글자 업데이트
            itemView.findViewById<TextView>(R.id.box_1).text = box[0]
            itemView.findViewById<TextView>(R.id.box_2).text = box[1]
            itemView.findViewById<TextView>(R.id.box_3).text = box[2]
            itemView.findViewById<TextView>(R.id.box_4).text = box[3]
            itemView.findViewById<TextView>(R.id.box_5).text = box[4]
            itemView.findViewById<TextView>(R.id.box_6).text = box[5]
            itemView.findViewById<TextView>(R.id.box_7).text = box[6]
            itemView.findViewById<TextView>(R.id.box_8).text = box[7]
            itemView.findViewById<TextView>(R.id.box_9).text = box[8]
            itemView.findViewById<TextView>(R.id.box_10).text = box[9]
            itemView.findViewById<TextView>(R.id.box_11).text = box[10]
            itemView.findViewById<TextView>(R.id.box_12).text = box[11]
            itemView.findViewById<TextView>(R.id.box_13).text = box[12]
            itemView.findViewById<TextView>(R.id.box_14).text = box[13]
            itemView.findViewById<TextView>(R.id.box_15).text = box[14]
            itemView.findViewById<TextView>(R.id.box_16).text = box[15]
            itemView.findViewById<TextView>(R.id.box_17).text = box[16]
            itemView.findViewById<TextView>(R.id.box_18).text = box[17]
            itemView.findViewById<TextView>(R.id.box_19).text = box[18]
            itemView.findViewById<TextView>(R.id.box_20).text = box[19]
            itemView.findViewById<TextView>(R.id.box_21).text = box[20]
            itemView.findViewById<TextView>(R.id.box_22).text = box[21]
            itemView.findViewById<TextView>(R.id.box_23).text = box[22]
            itemView.findViewById<TextView>(R.id.box_24).text = box[23]
            itemView.findViewById<TextView>(R.id.box_25).text = box[24]



            itemView.findViewById<TextView>(R.id.back).text = when {
                isGameOver == true && winner != null -> "${winner} 승리!"
                isGameOver == true -> "무승부"
                else -> "되돌아가기"
            }

            button.setOnClickListener{
                viewModel.goToState(position)
                updateItems(goToState(position))
            }

        }

    }


    // 서랍도 똑같은 방식으로 데이터 저장\

    private var history =  mutableListOf<ListItem>(ListItem.StartItem("게임 시작"))

    fun saveState(state: ListItem) {
        history.add(state)
    } // 자동으로 각 state 저장하게 끔 하는 ..

    fun goToState(index: Int): List<ListItem> {
        if (index in history.indices) {  // index가 0보다 큰 경우에만 index-1 접근
//            if (index > 0) {
//                items[index] = history[index]
//            } else {
//                items[index] = history[index]
//            }

            val newItems = history.take(index+1)
            history = history.take(index+1).toMutableList()
            return newItems
        }
        return emptyList()
    }

    fun updateItems(newItems: List<ListItem>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    fun addItem(item: ListItem) {
        items.add(item)  // 리스트에 새로운 아이템 추가
        saveState(this.items[items.size-1])
        notifyItemInserted(items.size - 1)  // 새로운 아이템이 추가된 위치에서 RecyclerView에 갱신 요청
    }


    override fun getItemCount(): Int = items.size


    fun updateBoard(state: TicTacToeState) {
        if (state.board != List(25) { "" } && state.isGameOver != true)
            this.addItem(ListItem.AfterItem(RecordState(state.board))) // recordAdapter에 보드 데이터 할당
        else {
            var text : String = ""

            if (state.isGameOver == true && state.winner != null )
            {text = "${state.winner} 승리!"
                this.addItem(ListItem.EndItem(RecordState(state.board), text))
            }
            else if (state.isGameOver == true)
            {text = "무승부"
                this.addItem(ListItem.EndItem(RecordState(state.board), text))}
        }

        }
    }

