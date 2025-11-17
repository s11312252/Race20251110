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

    //val horse = Horse()
    val horses = mutableListOf<Horse>()


    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h
    }

    fun StartGame() {
        circleX = 100f
        circleY = screenHeightPx - 100f

        for (i in 0..2) {
            horses.add(Horse(i))
        }


        viewModelScope.launch {
            while (gameRunning) {
                delay(100)
                circleX += 10

                if (circleX >= screenWidthPx - 100f) {
                    score++
                    circleX = 100f
                }

                for (i in 0..2) {
                    horses[i].HorseRun()
                    if (horses[i].horseX >= screenWidthPx - 200) {
                        horses[i].horseX = 0
                    }
                }
            }
        }
    }
    fun MoveCircle(x: Float, y: Float) {
        circleX += x
        circleY += y
    }
}


