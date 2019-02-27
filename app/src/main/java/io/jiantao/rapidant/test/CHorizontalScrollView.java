package io.jiantao.rapidant.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * description
 *
 * @author Created by jiantaoyang
 * @date 2019/2/26
 */
public class CHorizontalScrollView extends HorizontalScrollView {
    private static final String TAG = CHorizontalScrollView.class.getSimpleName();

    public CHorizontalScrollView(Context context) {
        super(context);
    }

    public CHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = super.onInterceptTouchEvent(ev);
        Log.d(TAG, "onInterceptTouchEvent result " + intercept);
        return intercept;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean dispatch = super.dispatchTouchEvent(ev);
        Log.d(TAG, "dispatchTouchEvent result " + dispatch);
        return dispatch;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean onTouchEventResult = super.onTouchEvent(ev);
        Log.d(TAG, "onTouchEvent result " + onTouchEventResult);
        return onTouchEventResult;
    }
}
