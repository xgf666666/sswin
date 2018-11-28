package com.micropole.sxwine.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by: xudiwei
 * <p>
 * on: 2017/4/10.
 * <p>
 * 描述：四格密码输入框
 */

public class PasswordView extends EditText {
    private static final String TAG = "PasswordView";
    private Paint mPaint;
    //底色
    private int bgColor = Color.WHITE;
    //格子的底色
    private int gridColor = Color.LTGRAY;
    //点的颜色
    private int pointColor = Color.WHITE;
    //密码长度
    private int pswLen = 6;
    //小格子之间的距离
    private int divWidth = 20;
    private RectF mGridRectF = null;
    //输入的文本长度
    private int inputTextLen;

    public PasswordView(Context context) {
        super(context);
        init();
    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int realWidth = ((getMeasuredHeight() + divWidth) * pswLen) - divWidth;
        setMeasuredDimension(realWidth, height);

    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        inputTextLen = text.length();
        if (mInputPswConfirmLengthListener!=null&&inputTextLen==pswLen){//判断是否满足密码输入长度
            mInputPswConfirmLengthListener.InputPswConfirmLength();
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        //画背景色
        canvas.drawColor(bgColor);

        //画格子
        mPaint.setColor(gridColor);
        int left = 0;
        int right = 0;
        for (int i = 0; i < pswLen; i++) {
            left = (getHeight() + divWidth) * i;
            right = left + getHeight();
            mGridRectF = new RectF(left, 0, right, getHeight());
            canvas.drawRoundRect(mGridRectF, 10, 10, mPaint);
        }

        //画点
        mPaint.setColor(pointColor);
        int pointX;
        int pointY = getHeight() / 2;
        for (int i = 0; i < inputTextLen; i++) {
            pointX = ((getHeight() + divWidth) * i) + (getHeight() / 2);
            canvas.drawCircle(pointX, pointY, 10, mPaint);
        }
    }

    /**
     * 满足密码输入长度监听
     */
    private InputPswConfirmLengthListener mInputPswConfirmLengthListener;

    public interface InputPswConfirmLengthListener{
        void InputPswConfirmLength();
    }

    public void setInputPswConfirmLengthListener(InputPswConfirmLengthListener listener){
        mInputPswConfirmLengthListener=listener;
    }

}
