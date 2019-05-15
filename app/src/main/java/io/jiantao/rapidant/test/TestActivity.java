package io.jiantao.rapidant.test;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import io.jiantao.rapidant.R;
import io.jiantao.utils.android.LifecycleHandler;
import io.jiantao.utils.android.UiThreadUtil;

/**
 * verify same skills
 * 1. horizontalScrollView 会拦截父布局的点击事件，因为自身onTouchEvent始终返回true，并且没有执行自身的onClick事件
 * 2. 验证handler消息去重。
 * 2.1 发送相同what的空消息不会去重。
 * 2.2 post同一个runnable 也不会去重。
 * 2.3 发送同一个Message会抛异常。
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = TestActivity.class.getSimpleName();

    TextView testText;
    Handler handler;

    Dialog mDialog;

    int msgWhat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Log.d(TAG, "onCreate");

        findViewById(R.id.fl_content).setOnClickListener(this);
        findViewById(R.id.hsv_scrollview).setOnClickListener(this);
        findViewById(R.id.iv_imageview).setOnClickListener(this);

        testText = findViewById(R.id.tv_test_text);

        testText.setText(Html.fromHtml(getString(R.string.order_item_intro, 5, "500")));


        handler = new LifecycleHandler(this, new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Log.d(TAG, "handleMessage >>>>>> msg.what " + msg.what);
                String message = "msg.what = " + msg.what;
                testText.setText(message);
                showTestDialog(message);
                Log.d(TAG, "handleMessage <<<<<< msg.what " + msg.what);
                msgWhat++;
                handler.sendEmptyMessageDelayed(msgWhat, 300);
                return false;
            }
        });

        handler.sendEmptyMessageDelayed(msgWhat, 300);

        UiThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 5000);

    }

    private void showTestDialog(String message) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        mDialog = new AlertDialog.Builder(this).setMessage(message).setPositiveButton("ok", null).create();
        mDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick v " + v.getId());
    }
}
