package com.vaulert.vui.button;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.vaulert.vui.R;

/**
 * 倒计时 Button
 */
public class CountDownButton extends AppCompatButton {

    private int mCount = 60;
    private Handler mHandler;
    private String mDefaultColor = "#4671DD";
    private String mCountColor = "#ff000000";

    public CountDownButton(@NonNull Context context) {
        super(context);
        init();
    }

    public CountDownButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CountDownButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setText("获取验证码");
        this.setTextColor(Color.parseColor(mDefaultColor));
        this.setBackgroundResource(R.drawable.sp_bg_count_down);
        mHandler = new Handler();
    }

    Runnable countDown = new Runnable() {
        @Override
        public void run() {
            setText(mCount + "S");
            setTextColor(Color.parseColor(mCountColor));
            setEnabled(false);
            setBackgroundResource(R.drawable.sp_bg_count_down);

            if (mCount > 0) {
                mHandler.postDelayed(this, 1000);
            } else {
                resetCount();
            }
            mCount--;
        }
    };

    public void start() {
        mHandler.postDelayed(countDown, 0);
    }

    public void resetCount(String... text) {
        this.setEnabled(true);
        if (!TextUtils.isEmpty(text.toString()) && "" != text[0]) {
            this.setText(text[0]);
        } else {
            this.setText("重新获取验证码");
        }
        mCount = 60;
        this.setBackgroundResource(R.drawable.sp_bg_count_down);
        this.setTextColor(Color.parseColor(mDefaultColor));
    }

    public void removeRunnable() {
        mHandler.removeCallbacks(countDown);
    }
}
