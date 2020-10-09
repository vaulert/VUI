package com.vaulert.vui.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.vaulert.vui.R;

/**
 * 弹窗气泡效果的ImageView
 * Android自定义圆角以及向下箭头的ImageView
 * 引自: https://blog.csdn.net/dreamsever/article/details/76615281
 */
public class PopImageView extends AppCompatImageView {

    private Paint paint;
    private Path muskPath;
    private int roundRadius;//圆角半径
    private int angleHeight;//下角高度
    private float percent = 0.3f;//下角在底部的左边占据百分比
    private Bitmap mRectMask;
    private Xfermode mXfermode;

    public PopImageView(Context context) {
        this(context, null);
    }

    public PopImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PopImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PopImageView);
        roundRadius = typedArray.getDimensionPixelOffset(R.styleable.PopImageView_roundRadius, 0);
        angleHeight = typedArray.getDimensionPixelOffset(R.styleable.PopImageView_angleHeight, 0);
        percent = typedArray.getFloat(R.styleable.PopImageView_anglePercent, 0);
        typedArray.recycle();
        // 关键方法
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int id = canvas.saveLayer(0, 0, getWidth(), getHeight(), null);
        super.onDraw(canvas);
        Drawable drawable = getDrawable();
        if (null != drawable) {
            createMask();
            // 关键方法
            paint.setXfermode(mXfermode);
            canvas.drawBitmap(mRectMask, 0, 0, paint);
            paint.setXfermode(null);
            canvas.restoreToCount(id);
        }
    }

    /**
     * 获取上层蒙层
     */
    private void createMask() {
        if (mRectMask == null) {
            int maskWidth = getMeasuredWidth();
            int maskHeight = getMeasuredHeight();
            mRectMask = Bitmap.createBitmap(maskWidth, maskHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(mRectMask);

            muskPath = new Path();
            muskPath.moveTo(roundRadius, 0);
            muskPath.lineTo(maskWidth - roundRadius, 0);
            muskPath.arcTo(new RectF(maskWidth - roundRadius * 2, 0, maskWidth, roundRadius * 2), 270, 90);
            muskPath.lineTo(maskWidth, maskHeight - roundRadius - angleHeight);
            muskPath.arcTo(new RectF(maskWidth - roundRadius * 2, maskHeight - roundRadius * 2 - angleHeight, maskWidth, maskHeight - angleHeight), 0, 90);
            muskPath.lineTo(maskWidth * percent + angleHeight, maskHeight - angleHeight);
            muskPath.lineTo(maskWidth * percent, maskHeight);
            muskPath.lineTo(maskWidth * percent - angleHeight, maskHeight - angleHeight);
            muskPath.lineTo(roundRadius, maskHeight - angleHeight);
            muskPath.arcTo(new RectF(0, maskHeight - roundRadius * 2 - angleHeight, roundRadius * 2, maskHeight - angleHeight), 90, 90);
            muskPath.lineTo(0, roundRadius);
            muskPath.arcTo(new RectF(0, 0, roundRadius * 2, roundRadius * 2), 180, 90);

            canvas.drawPath(muskPath, paint);
        }
    }
}