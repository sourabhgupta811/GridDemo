package co.markai.interviewapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class SecondActivityViewModel: ViewModel() {
    data class Point(val x: Int, val y: Int)
    data class GridData(val rowCount: Int, val colCount: Int, val points: Pair<Point, Point>?)
    private val gridData = MutableStateFlow<GridData?>(null)
    fun gridFlow() = gridData as StateFlow<GridData?>
    fun setData(rowCount: Int, colCount: Int){
        gridData.value = GridData(rowCount, colCount, null)
    }

    fun calculateRandomPos(data: GridData): Pair<Point, Point>{
        // move the computation to a coroutine
        val gridData = data
        val image1PosX = Random.nextInt(0, gridData.colCount)
        val image1Posy = Random.nextInt(0, gridData.rowCount)
        var image2PosX: Int
        var image2PosY: Int

        do {
            image2PosX = Random.nextInt(0, gridData.colCount)
            image2PosY = Random.nextInt(0, gridData.rowCount)
        } while (image2PosX == image1PosX || image2PosY == image1Posy)

        return Pair(Point(image1PosX, image1Posy), Point(image2PosX, image2PosY))
    }

    fun updateData(points: Pair<Point, Point>) {
        // launch in coroutine
        gridData.update {
            it?.copy(points = points)
        }
    }
}