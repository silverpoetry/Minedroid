package com.example.minedroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null)actionBar.hide();

        MapManager mapManager = new MapManager(this,MapManager.GameDifficulty.HARD);
        mapManager.generateMap();
        mapManager.generateButtons();

    }
}