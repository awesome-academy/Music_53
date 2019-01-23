package com.trantan.music53.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.trantan.music53.R;
import com.trantan.music53.service.music.PlayService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startPlayService();
    }

    private void startPlayService() {
        Intent intent = new Intent(this, PlayService.class);
        startService(intent);
    }
}
