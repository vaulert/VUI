package com.vaulert.vui.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * https://www.jb51.net/article/122307.htm
 */
public class CircleDrawable extends Drawable {

    private Paint mPaint;
    private int mWidth;
    private Bitmap mBitmap;

    public CircleDrawable(Bitmap bitmap) {
        mBitmap = bitmap;
        //着色器，设置横向和纵向的着色模式为平铺
        BitmapShader bitmapShader = new BitmapShader(mBitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setShader(bitmapShader);
        mWidth = Math.min(mBitmap.getWidth(), mBitmap.getHeight());
    }

    //绘制
    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2, mPaint);
    }

    //设置透明度值
    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    //设置颜色过滤器
    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    //返回不透明度
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    //返回图片实际的宽高
    @Override
    public int getIntrinsicWidth() {
        return mWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mWidth;
    }
}

