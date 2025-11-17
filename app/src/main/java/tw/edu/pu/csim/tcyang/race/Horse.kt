package tw.edu.pu.csim.tcyang.race

class Horse(n: Int) {
    // Horse 的初始 Y 位置是根據 n 計算的
    var horseX = 0
    var horseY = 100+ 220 * n

    var number = 0

    /**
     * 重設馬匹到初始位置。
     * @param n 馬匹的編號 (0, 1, 2)，用於計算 Y 軸位置。
     */
    fun Reset(n: Int) {
        horseX = 0
        horseY = 100 + 220 * n // 重設到初始 Y 軸位置
        number = 0 // 重設動畫幀數
    }

    fun HorseRun(){
        number ++
        if (number>3) {
            number = 0
        }

        horseX += (10..30).random()
    }
}