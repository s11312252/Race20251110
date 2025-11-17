package tw.edu.pu.csim.tcyang.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

@Composable
fun GameScreen(message: String, gameViewModel: GameViewModel) {

    val imageBitmaps = listOf(
        ImageBitmap.imageResource(R.drawable.horse0),
        ImageBitmap.imageResource(R.drawable.horse1),
        ImageBitmap.imageResource(R.drawable.horse2),
        ImageBitmap.imageResource(R.drawable.horse3)
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Yellow)
    ){
        Canvas (modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    gameViewModel.MoveCircle( dragAmount.x, dragAmount.y)
                }
            }
        ) {
            drawCircle(
                color = Color.Red,
                radius = 100f,
                center = Offset(gameViewModel.circleX, gameViewModel.circleY)
            )
            for(i in 0..2){
                drawImage(
                    image = imageBitmaps[gameViewModel.horses[i].number],
                    dstOffset = IntOffset(
                        gameViewModel.horses[i].horseX,
                        gameViewModel.horses[i].horseY),
                    dstSize = IntSize(200, 200)
                )
            }
        }

        // 顯示遊戲標題、螢幕尺寸和分數
        var displayMessage = message + "\n" +
                "螢幕尺寸: " + gameViewModel.screenWidthPx.toString() + " x " +
                gameViewModel.screenHeightPx.toString() + "\n" +
                "目前分數: " + gameViewModel.score.toString()

        // *** 變更 2: 顯示獲勝訊息 ***
        if (gameViewModel.winnerNumber != 0) {
            displayMessage += "\n第 ${gameViewModel.winnerNumber} 馬獲勝!"
        }

        Text(text = displayMessage)

        Button(onClick = {
            // 確保遊戲只啟動一次
            if (!gameViewModel.gameRunning) {
                gameViewModel.gameRunning = true
                gameViewModel.StartGame()
            }
        }) {
            Text(text = "遊戲開始")
        }
    }
}