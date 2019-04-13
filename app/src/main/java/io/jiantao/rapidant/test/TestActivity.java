package io.jiantao.rapidant.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import io.jiantao.rapidant.R;
import io.jiantao.utils.java.FileUtils;

/**
 * verify same skills
 * 1. horizontalScrollView 会拦截父布局的点击事件，因为自身onTouchEvent始终返回true，并且没有执行自身的onClick事件
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = TestActivity.class.getSimpleName();

    TextView testText;
    Handler handler;
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

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    Log.d(TAG, "handleMessage >>>>>> msg.what "+msg.what);
                    Thread.sleep(2000);
                    Log.d(TAG, "handleMessage <<<<<< msg.what "+msg.what);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "test runnable >>>>>> msg.what ");
                    Thread.sleep(2000);
                    Log.d(TAG, "test runnable <<<<<< msg.what ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Message msg = Message.obtain();

        int count = 10;
        for (int i = 0; i < count; i++) {
            boolean result = false;
            // 1. 发送相同what的空消息不会去重。
//            handler.sendEmptyMessageDelayed(1, 50);
            // 2. post同一个runnable 也不会去重。
//            result = handler.postDelayed(runnable, 50);
            // 3. 发送同一个Message会抛出异常。

            msg.what = i;
            result = handler.sendMessageDelayed(msg, 50);

            Log.d(TAG, "test Handler post "+result);
        }
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
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    public void wanAndroid(View view){
        startActivity(new Intent(this, io.jiantao.example.wanandroid.MainActivity.class));
    }

    public void wanAndroiddb(View view){
        final String dbName = "rapid_ant.db";
        File dbFile = getDatabasePath(dbName).getParentFile();
        File destFile = new File(Environment.getExternalStorageDirectory(), "rapidAntDbDir");
        boolean state = FileUtils.copyDir(dbFile, destFile);
        Toast.makeText(this, "拷贝数据库 "+(state?"成功":"失败"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick v "+v.getId());
    }
}
