data class TicTacToeState(
    val board: List<String> = List(25) { "" },  // 9개의 칸을 빈 문자열로 초기화
    val currentPlayer: String = "X",  // 현재 플레이어는 X로 시작
    val isGameOver: Boolean = false,
    val winner: String? = null  // 승자가 있을 경우 해당 플레이어("X" 또는 "O"), 없으면 null
)
