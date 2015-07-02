package com.tubb.dotview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bingbing.tu
 * 2015/7/2.
 */
public class DotView extends View {

    private Drawable drawableLeft;
    private Drawable drawableRight;
    private int dotMarginTop = 0;
    private int dotMarginRight = 0;

    public DotView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DotView, 0, defStyleAttr);
        drawableLeft = a.getDrawable(R.styleable.DotView_android_drawableLeft);
        drawableRight = a.getDrawable(R.styleable.DotView_android_drawableRight);
        dotMarginTop = (int)(a.getDimension(R.styleable.DotView_dotMarginTop, dotMarginTop) + 0.5f);
        dotMarginRight = (int)(a.getDimension(R.styleable.DotView_dotMarginRight, dotMarginRight) + 0.5f);
        if(drawableLeft == null) throw new IllegalArgumentException("drawableLeft attribute must apply...");

        try{
            a.recycle();
        }catch (Exception e){
            // nothing
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Get the width measurement
        int widthSize = getMeasurement(widthMeasureSpec, getDesiredWidth());

        //Get the height measurement
        int heightSize = getMeasurement(heightMeasureSpec, getDesiredHeight());

        //MUST call this to store the measurements
        setMeasuredDimension(widthSize, heightSize);
    }

    private int getDesiredWidth() {
        int drawbleWidth = drawableLeft.getIntrinsicWidth();
        if (drawableRight == null) return drawbleWidth;
        return drawableRight.getIntrinsicWidth() + drawbleWidth;
    }

    private int getDesiredHeight() {
        int drawbleHeight = drawableLeft.getIntrinsicHeight();
        if (drawableRight == null) return drawbleHeight;
        return drawableRight.getIntrinsicHeight() + drawbleHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawableLeft.draw(canvas);
        if(drawableRight != null) drawableRight.draw(canvas);
    }

    public static int getMeasurement(int measureSpec, int contentSize) {
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        int resultSize = 0;
        switch (specMode) {
            case View.MeasureSpec.UNSPECIFIED:
                //Big as we want to be
                resultSize = contentSize;
                break;
            case View.MeasureSpec.AT_MOST:
                //Big as we want to be, up to the spec
                resultSize = Math.min(contentSize, specSize);
                break;
            case View.MeasureSpec.EXACTLY:
                //Must be the spec size
                resultSize = specSize;
                break;
        }

        return resultSize;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            updateContentBounds();
        }
    }

    private void updateContentBounds() {
        if(drawableRight != null){
            drawableLeft.setBounds(drawableRight.getIntrinsicWidth()/2 , drawableRight.getIntrinsicHeight()/2,
                    drawableLeft.getIntrinsicWidth() + drawableRight.getIntrinsicWidth()/2, drawableLeft.getIntrinsicHeight()+drawableRight.getIntrinsicHeight()/2);
            drawableRight.setBounds(drawableLeft.getIntrinsicWidth() - dotMarginRight, dotMarginTop,
                    drawableLeft.getIntrinsicWidth() + drawableRight.getIntrinsicWidth() - dotMarginRight, drawableRight.getMinimumHeight() + dotMarginTop);
        }else{
            drawableLeft.setBounds(0 , 0, drawableLeft.getIntrinsicWidth(), drawableLeft.getIntrinsicHeight());
        }
    }

    public void setDrawableLeft(@DrawableRes int drawableLeftRes) {
        Drawable l = getResources().getDrawable(drawableLeftRes);
        setDrawableLeft(l);
    }

    public void setDrawableRight(@DrawableRes int drawbleRightRes) {
        Drawable r = getResources().getDrawable(drawbleRightRes);
        setDrawableRight(r);
    }

    public void setDrawableLeft(Drawable drawableLeft) {
        if(drawableLeft == null) throw new IllegalArgumentException("drawableLeft cant not be null...");
        this.drawableLeft = drawableLeft;
        updateContentBounds();
        invalidate();
    }

    public void setDrawableRight(Drawable drawbleRight) {
        this.drawableRight = drawbleRight;
        updateContentBounds();
        invalidate();
    }
}
