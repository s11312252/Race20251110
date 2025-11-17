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

    var winnerNumber by mutableStateOf(0)

    val horses = mutableListOf<Horse>().apply {
        add(Horse(0))
        add(Horse(1))
        add(Horse(2))
    }

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
        winnerNumber = 0

        for (i in 0..2) {
            horses[i].Reset(i)
        }

        viewModelScope.launch {
            while (gameRunning) {
                delay(100)

                circleX += 10
                if (circleX >= screenWidthPx - 100f) {
                    score++
                    circleX = 100f
                }

                if (winnerNumber == 0) {
                    for (i in 0..2) {
                        horses[i].HorseRun()

                        // 檢查是否到達終點
                        if (horses[i].horseX >= finishLine) {
                            winnerNumber = i + 1
                            // 馬匹回到起點
                            for (j in 0..2) {
                                horses[j].Reset(j)
                            }
                            break
                        }
                    }
                } else {
                }
            }
        }
    }
    fun MoveCircle(x: Float, y: Float) {
        circleX += x
        circleY += y
    }
}