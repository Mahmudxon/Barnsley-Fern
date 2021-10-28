package uz.mahmudxon.barnsleyfern.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


class FernView(
    context: Context,
    attrs: AttributeSet? = null
) : androidx.appcompat.widget.AppCompatImageView(context, attrs) {

    private var _x = 0f
    private var _y = 0f
    private var isFirstOpen = true

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (isFirstOpen) {
            isFirstOpen = false
            createImage()
        }
    }

    private fun createImage() {
        val conf = Bitmap.Config.ARGB_8888
        val bitmap = Bitmap.createBitmap(width, height, conf)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        canvas.drawPaint(paint)
        val p = Paint()
        p.color = Color.parseColor("#1ba405")

        CoroutineScope(Main).launch {
            for (i in 0 until 1_000_000_001) {
                nextPoint()
                val x = _x
                val y = _y
                val pX = width * (x + 3) / 6
                val pY = height - height * ((y + 2) / 14)
                canvas.drawCircle(pX, pY, 1f, p)
                if (i % 100 == 0) {
                    delay(10)
                    setImageBitmap(bitmap)
                }
            }
        }

    }


    private fun nextPoint() {
        val nextX: Float
        val nextY: Float
        val r: Float = Random.nextInt(100) / 100f
        when {
            r < 0.01 -> {
                nextX = 0f
                nextY = (0.16 * _y).toFloat()
            }
            r < 0.86 -> {
                nextX = (0.85 * _x + 0.04 * _y).toFloat()
                nextY = (-0.04 * _x + 0.85 * _y + 1.6).toFloat()
            }
            r < 0.93 -> {
                nextX = (0.20 * _x - 0.26 * _y).toFloat()
                nextY = (0.23 * _x + 0.22 * _y + 1.6).toFloat()
            }
            else -> {
                nextX = (-0.15 * _x + 0.28 * _y).toFloat()
                nextY = (0.26 * _x + 0.24 * _y + 0.44).toFloat()
            }
        }
        _x = nextX
        _y = nextY
    }
}