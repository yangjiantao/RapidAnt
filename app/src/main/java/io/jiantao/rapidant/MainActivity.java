package io.jiantao.rapidant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void wanAndroid(View view){
        startActivity(new Intent(this, io.jiantao.example.wanandroid.MainActivity.class));
    }
}
