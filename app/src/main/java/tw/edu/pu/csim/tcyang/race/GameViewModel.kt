package tw.edu.pu.csim.tcyang.race

import android.R.attr.value
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel: ViewModel() {

    var screenWidthPx by mutableStateOf(0f)
        private set

    var screenHeightPx by mutableStateOf(0f)
        private set

    var gameRunning by mutableStateOf(false)

    var circleX by mutableStateOf(value = 0f)

    var circleY by mutableStateOf(value = 0f)

    var score by mutableStateOf(0)
        private set

    // *** 變更 2: 新增獲勝馬匹編號，0 表示無獲勝者 ***
    var winnerNumber by mutableStateOf(0)

    val horses = mutableListOf<Horse>().apply {
        add(Horse(0))
        add(Horse(1))
        add(Horse(2))
    }

    // 設定馬匹到達終點的 X 座標 (預留 200px 給馬匹圖片)
    private val finishLine: Float
        get() = screenWidthPx - 200f


    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h
    }

    fun StartGame() {
        circleX = 100f
        circleY = screenHeightPx - 100f
        score = 0
        winnerNumber = 0 // 重置獲勝者

        // 重置所有馬匹到初始狀態
        for (i in 0..2) {
            horses[i].Reset(i)
        }

        viewModelScope.launch {
            while (gameRunning) {
                delay(100)

                // 處理圓形移動
                circleX += 10
                if (circleX >= screenWidthPx - 100f) {
                    score++
                    circleX = 100f
                }

                // *** 變更 2: 處理賽馬邏輯 ***
                if (winnerNumber == 0) { // 只有在沒有獲勝者時才讓馬跑
                    for (i in 0..2) {
                        horses[i].HorseRun()

                        // 檢查是否到達終點
                        if (horses[i].horseX >= finishLine) {
                            winnerNumber = i + 1 // i=0 是第 1 馬, i=1 是第 2 馬...
                            // 馬匹回到起點
                            for (j in 0..2) {
                                horses[j].Reset(j)
                            }
                            break // 找到獲勝者後就跳出迴圈
                        }
                    }
                } else {
                    // 如果已經有獲勝者，可以讓遊戲暫停或等待使用者點擊
                    // 在這裡，我們讓遊戲繼續運行，但馬匹已重置到起點
                }
            }
        }
    }
    fun MoveCircle(x: Float, y: Float) {
        circleX += x
        circleY += y
    }
}