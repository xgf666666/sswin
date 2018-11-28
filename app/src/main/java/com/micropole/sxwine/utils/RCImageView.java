package com.micropole.sxwine.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * RCImageView
 * (。・∀・)ノ
 * Describe： Rounded corners ImageView
 * Created by 雷小星🍀 on 2017/8/1 15:37.
 */

public class RCImageView extends BaseImageView {

    private int degree = 12;//圆角程度,默认为8

    public RCImageView(Context context) {
        super(context);
    }

    public RCImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RCImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public static Bitmap getBitmap(int degree, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        canvas.drawRoundRect(new RectF(0.0f, 0.0f, width, height), degree, degree, paint);
        return bitmap;
    }

    public int getDegree() {
        return degree;
    }

    /**
     * 设置ImageView的圆角程度
     *
     * @param degree
     */
    public void setDegree(int degree) {
        this.degree = degree;
    }

    @Override
    public Bitmap getBitmap() {
        return getBitmap(getDegree(), getWidth(), getHeight());
    }
}
