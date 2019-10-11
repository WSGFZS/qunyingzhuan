package wangsheng.swpuiot.qunyingzhuan.ch3.section6;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;

public class MyCustonTextView extends AppCompatTextView {
    private Paint mpaint1;
    private Paint mpaint2;
    private int mViewWidth;
    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private int mTranslate;
    private TextPaint mPaint;

    public MyCustonTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mpaint1 = new Paint();
        mpaint1.setColor(getResources().getColor(android.R.color.holo_blue_light));
        mpaint1.setStyle(Paint.Style.FILL);
        mpaint2 = new Paint();
        mpaint2.setStyle(Paint.Style.FILL);
        mpaint2.setColor(Color.YELLOW);

    }

    public MyCustonTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCustonTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //回调方法类前，实现自己的逻辑，对TextView而言即是绘制文本前
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mpaint1);
        canvas.drawRect(40, 40, getMeasuredWidth() - 40, getMeasuredHeight() - 40, mpaint2);
        canvas.save();
        //绘制文字前平移10像素
        super.onDraw(canvas);//父类完成的方法，绘制文字
        //在回调方法后，实现自己的逻辑，对TextView来说即是在绘制文本后
        if (mGradientMatrix != null) {
            mTranslate += mViewWidth / 5;
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);
        }
        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                //获取当前绘制TextView的Paint对象
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0, new int[]{
                        Color.BLUE, 0xffffffff, Color.BLUE
                }, null, Shader.TileMode.CLAMP);
            }
        }
        mPaint.setShader(mLinearGradient);
        mGradientMatrix = new Matrix();
    }


}
