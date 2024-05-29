package com.aksarabatak.yolov8tflite

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import java.util.LinkedList
import kotlin.math.max

class OverlayView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var results = listOf<BoundingBox>()
    private var boxPaint = Paint()
    private var textBackgroundPaint = Paint()
    private var textPaint = Paint()

    private var bounds = Rect()

    init {
        initPaints()
    }

    fun clear() {
        textPaint.reset()
        textBackgroundPaint.reset()
        boxPaint.reset()
        invalidate()
        initPaints()
    }

    private fun initPaints() {
        textBackgroundPaint.color = Color.parseColor("#f1f1f1")
        textBackgroundPaint.style = Paint.Style.FILL
        textBackgroundPaint.textSize = 30f

        textPaint.color = Color.parseColor("#393939")
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 30f

        boxPaint.color = Color.parseColor("#c21d03")
        boxPaint.strokeWidth = 8F
        boxPaint.style = Paint.Style.STROKE
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        val bitmapWidth = width
        val bitmapHeight = height

        results.filter { it.cnf >= 0.5f }.forEach {
            val left = it.x1 * bitmapWidth
            val top = it.y1 * bitmapHeight
            val right = it.x2 * bitmapWidth
            val bottom = it.y2 * bitmapHeight

            canvas.drawRect(left, top, right, bottom, boxPaint)
            val roundedConfidence = (it.cnf * 100).toInt()
            val drawableText = "${it.clsName} $roundedConfidence%"

            textBackgroundPaint.getTextBounds(drawableText, 0, drawableText.length, bounds)
            val textWidth = bounds.width()
            val textHeight = bounds.height()
            canvas.drawRect(
                left,
                top,
                left + textWidth + BOUNDING_RECT_TEXT_PADDING,
                top + textHeight + BOUNDING_RECT_TEXT_PADDING,
                textBackgroundPaint
            )
            canvas.drawText(drawableText, left, top + bounds.height(), textPaint)

        }
    }

    fun setResults(boundingBoxes: List<BoundingBox>) {
        results = boundingBoxes
        invalidate()
    }

    companion object {
        private const val BOUNDING_RECT_TEXT_PADDING = 8
    }
}
