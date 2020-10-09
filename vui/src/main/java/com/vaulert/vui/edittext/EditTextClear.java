package com.vaulert.vui.edittext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.vaulert.vui.R;

/**
 * 带有清除文本的 EditText
 */
public class EditTextClear extends AppCompatEditText implements
        View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {

    private Drawable mClearTextIcon;
    private ClearCallback callback;
    private OnTouchListener mOnTouchListener;
    private OnFocusChangeListener mOnFocusChangeListener;

    public EditTextClear(@NonNull Context context) {
        super(context);
        init(context);
    }

    public EditTextClear(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EditTextClear(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // init drawable
//        MyCustomDrawable drawable = new MyCustomDrawable(context, 20, 20);
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_vector_close);
        Drawable wrap = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrap, getCurrentHintTextColor());
        mClearTextIcon = wrap;
        mClearTextIcon.setBounds(0, 0,
                mClearTextIcon.getIntrinsicWidth(),
                mClearTextIcon.getIntrinsicHeight());
        // option
//        setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        // icon visible
        setClearIconVisible(false);
        // listener
        setOnTouchListener(this);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isFocused()) {
            setClearIconVisible(!TextUtils.isEmpty(s));
        }
    }

    private void setClearIconVisible(boolean visible) {
        mClearTextIcon.setVisible(visible, false);
        Drawable[] drawables = getCompoundDrawables();
        setCompoundDrawables(
                drawables[0],
                drawables[1],
                visible ? mClearTextIcon : null,
                drawables[3]);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        mOnFocusChangeListener = l;
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        mOnTouchListener = l;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(!TextUtils.isEmpty(getText()));
        } else {
            setClearIconVisible(false);
        }
        if (mOnFocusChangeListener != null) {
            mOnFocusChangeListener.onFocusChange(v, hasFocus);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX();
        if (mClearTextIcon.isVisible() && x > getWidth() - getPaddingRight() - mClearTextIcon.getIntrinsicHeight()) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                setText("");
                setError(null);
                callback.onClear();
            }
            return true;
        }
        return mClearTextIcon != null && mOnTouchListener.onTouch(v, event);
    }

    interface ClearCallback {
        void onClear();
    }

    void setClearCallback(ClearCallback callback) {
        this.callback = callback;
    }
}
