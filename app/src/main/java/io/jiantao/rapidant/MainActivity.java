package io.jiantao.rapidant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import io.jiantao.utils.java.FileUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
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
}
