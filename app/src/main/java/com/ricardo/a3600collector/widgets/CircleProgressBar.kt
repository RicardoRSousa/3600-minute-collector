package com.ricardo.a3600collector.widgets

import android.view.View
import android.view.animation.DecelerateInterpolator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.graphics.Paint
import android.util.AttributeSet
import com.ricardo.a3600collector.R


/**
 * 3600-minute-collector
 * Created by Ricardo on 22/12/2018 00:16.
 */
class CircleProgressBar(context: Context, attrs: AttributeSet) : View(context, attrs) {

    /**
     * ProgressBar's line thickness
     */
    private var strokeWidth = 4f
    private var progress = 0f
    private var min = 0
    private var max = 100
    /**
     * Start the progress at 12 o'clock
     */
    private val startAngle = -90f
    private var color = Color.DKGRAY
    private var rectF: RectF? = null
    private var backgroundPaint: Paint? = null
    private var foregroundPaint: Paint? = null

    fun getStrokeWidth(): Float {
        return strokeWidth
    }

    fun setStrokeWidth(strokeWidth: Float) {
        this.strokeWidth = strokeWidth
        backgroundPaint!!.setStrokeWidth(strokeWidth)
        foregroundPaint!!.setStrokeWidth(strokeWidth)
        invalidate()
        requestLayout()//Because it should recalculate its bounds
    }

    fun getProgress(): Float {
        return progress
    }

    fun setProgress(progress: Float) {
        this.progress = progress
        invalidate()
    }

    fun getMin(): Int {
        return min
    }

    fun setMin(min: Int) {
        this.min = min
        invalidate()
    }

    fun getMax(): Int {
        return max
    }

    fun setMax(max: Int) {
        this.max = max
        invalidate()
    }

    fun getColor(): Int {
        return color
    }

    fun setColor(color: Int) {
        this.color = color
        backgroundPaint!!.setColor(adjustAlpha(color, 0.3f))
        foregroundPaint!!.setColor(color)
        invalidate()
        requestLayout()
    }

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        rectF = RectF()
        val typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircleProgressBar,
                0, 0)
        //Reading values from the XML layout
        try {
            strokeWidth = typedArray.getDimension(R.styleable.CircleProgressBar_progressBarThickness, strokeWidth)
            progress = typedArray.getFloat(R.styleable.CircleProgressBar_progress, progress)
            color = typedArray.getInt(R.styleable.CircleProgressBar_progressbarColor, color)
            min = typedArray.getInt(R.styleable.CircleProgressBar_min, min)
            max = typedArray.getInt(R.styleable.CircleProgressBar_max, max)
        } finally {
            typedArray.recycle()
        }

        backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        backgroundPaint!!.setColor(adjustAlpha(color, 0.3f))
        backgroundPaint!!.setStyle(Paint.Style.STROKE)
        backgroundPaint!!.setStrokeWidth(strokeWidth)

        foregroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        foregroundPaint!!.setColor(color)
        foregroundPaint!!.setStyle(Paint.Style.STROKE)
        foregroundPaint!!.setStrokeWidth(strokeWidth)
    }

    protected override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawOval(rectF, backgroundPaint)
        val angle = 360 * progress / max
        canvas.drawArc(rectF, startAngle, angle, false, foregroundPaint)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val height = View.getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val width = View.getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val min = Math.min(width, height)
        setMeasuredDimension(min, min)
        rectF!!.set(0 + strokeWidth / 2, 0 + strokeWidth / 2, min - strokeWidth / 2, min - strokeWidth / 2)
    }

    /**
     * Lighten the given color by the factor
     *
     * @param color  The color to lighten
     * @param factor 0 to 4
     * @return A brighter color
     */
    fun lightenColor(color: Int, factor: Float): Int {
        val r = Color.red(color) * factor
        val g = Color.green(color) * factor
        val b = Color.blue(color) * factor
        val ir = Math.min(255, r.toInt())
        val ig = Math.min(255, g.toInt())
        val ib = Math.min(255, b.toInt())
        val ia = Color.alpha(color)
        return Color.argb(ia, ir, ig, ib)
    }

    /**
     * Transparent the given color by the factor
     * The more the factor closer to zero the more the color gets transparent
     *
     * @param color  The color to transparent
     * @param factor 1.0f to 0.0f
     * @return int - A transplanted color
     */
    fun adjustAlpha(color: Int, factor: Float): Int {
        val alpha = Math.round(Color.alpha(color) * factor)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }

    /**
     * Set the progress with an animation.
     * Note that the [android.animation.ObjectAnimator] Class automatically set the progress
     * so don't call the [CircleProgressBar.setProgress] directly within this method.
     *
     * @param progress The progress it should animate to it.
     */
    fun setProgressWithAnimation(progress: Float) {

        val objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress)
        objectAnimator.duration = 1500
        objectAnimator.interpolator = DecelerateInterpolator()
        objectAnimator.start()
    }
}