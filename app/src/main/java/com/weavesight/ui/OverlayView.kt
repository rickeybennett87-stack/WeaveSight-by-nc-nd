
package com.weavesight.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.weavesight.sensors.ResonanceManager

class OverlayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint().apply {
        color = Color.GREEN
        textSize = 40f
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    var resonanceManager: ResonanceManager? = null

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        resonanceManager?.let {
            val resonance = it.resonanceValue
            val color = when {
                resonance > 100 -> Color.RED
                resonance > 60 -> Color.YELLOW
                else -> Color.GREEN
            }
            paint.color = color
            canvas.drawText("Weave: %.2f µT".format(resonance), 50f, 100f, paint)
        }
    }

    fun updateOverlay() {
        invalidate()
    }
}
