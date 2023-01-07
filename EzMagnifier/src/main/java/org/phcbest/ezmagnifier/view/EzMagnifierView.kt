package org.phcbest.ezmagnifier.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout

/**
 * @author phcbest
 * @date 2023/1/7 19:23
 * @github https://github.com/phcbest
 * ViewGroup触摸事件调用原则
 * dispatchTouchEvent()中，会对子View进行递归，将dispatchTouchEvent()传递给它的每一个子View
 *      如果某一个子View在ACTION_DOWN的时候没有消费或者拦截事件，那么这个子View后续也将无法收到ACTIION_MOVE和ACTION_UP等事件。
 * ViewGroup可以在onInterceptTouchEvent()中拦截事件，onInterceptTouchEvent()在之后执行，
 *      如果返回true，则会执行自身的onTouchEvent()方法，但后续onInterceptTouchEvent()和onTouchEvent()将不会调用。
 * ViewGroup如果onInterceptTouchEvent()返回false，那么ViewGroup后续是否继续调用dispatchTouchEvent()取决于子View是否消费触摸事件。
 * ViewGroup如果返回在dispatchTouchEvent()中返回false，那么后续所有的触摸操作都不会再执行！
 * ViewGroup在dispatchTouchEvent()中return true和return super.dispatchTouchEvent(ev)会导致触摸结果不一样！
 */
private const val TAG = "EzMagnifierView"

class EzMagnifierView : FrameLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        //enable draw
        setWillNotDraw(false)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    private var mOutStrokeRect = RectF()
    private var mMagnifierRect = RectF()
    private var mOutStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
        this.color = Color.parseColor("#66CCCC")
    }
    private var mMagnifierPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
        this.color = Color.parseColor("#000000")
    }

    private var mMagnifierSize = 300F
    private var mMagnifierPadding = 10F
    private var mMagnifierMargin = 8F
    private var mMagnifierRound = 15F
    private var isDrawMagnifierEnable = false

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (isDrawMagnifierEnable) {
            //draw rect in top corner
            canvas?.drawRoundRect(mOutStrokeRect, mMagnifierRound, mMagnifierRound, mOutStrokePaint)
            //set mMagnifierPaint shader
            canvas?.drawRoundRect(mMagnifierRect, mMagnifierRound, mMagnifierRound, mMagnifierPaint)
        }
    }

    /**
     * @param isLeft true in left ,false in right
     */
    private fun setMagnifierRectInLeft(isLeft: Boolean) {
        if (isLeft) {
            mOutStrokeRect.set(
                mMagnifierMargin,
                mMagnifierMargin,
                mMagnifierSize + mMagnifierMargin,
                mMagnifierSize + mMagnifierMargin
            )
            mMagnifierRect.set(
                mMagnifierMargin + mMagnifierPadding,
                mMagnifierMargin + mMagnifierPadding,
                mMagnifierSize + mMagnifierMargin - mMagnifierPadding,
                mMagnifierSize + mMagnifierMargin - mMagnifierPadding
            )
        } else {
            mOutStrokeRect.set(
                right - mMagnifierSize - mMagnifierMargin,
                mMagnifierMargin,
                right - mMagnifierMargin,
                mMagnifierSize + mMagnifierMargin
            )
            mMagnifierRect.set(
                right - mMagnifierSize - mMagnifierMargin + mMagnifierPadding,
                mMagnifierMargin + mMagnifierPadding,
                right - mMagnifierMargin - mMagnifierPadding,
                mMagnifierSize + mMagnifierMargin - mMagnifierPadding
            )
        }
    }

    private fun drawMagnifier(event: MotionEvent) {
        isDrawMagnifierEnable = true
        if (event.x in 0F..mMagnifierSize && event.y in 0F..mMagnifierSize) {
            setMagnifierRectInLeft(false)
        } else {
            setMagnifierRectInLeft(true)
        }
        invalidate()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.i(TAG, "onInterceptTouchEvent: $ev")
        return true
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.i(TAG, "dispatchTouchEvent: $event ")
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.i(TAG, "onTouchEvent: $event")
        when (event?.action?.and(MotionEvent.ACTION_MASK)) {
            MotionEvent.ACTION_DOWN -> {
                drawMagnifier(event)
            }
            MotionEvent.ACTION_UP -> {
                isDrawMagnifierEnable = false
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                drawMagnifier(event)
            }
            else -> {}
        }
        performClick()
        return true
    }


    /**
     * 视觉障碍帮助
     */
    override fun performClick(): Boolean {
        return super.performClick()
    }


}