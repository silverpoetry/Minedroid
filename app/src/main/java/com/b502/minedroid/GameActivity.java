package com.b502.minedroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.b502.minedroid.utils.MapManager;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSmile;
    private MapManager mapManager;
    MapManager.GameDifficulty dif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent i = getIntent();
        dif = MapManager.GameDifficulty.values()[i.getIntExtra("diff", 0)];
        btnSmile = findViewById(R.id.btnsmile);
        btnSmile.setOnClickListener(this);
        onClick(btnSmile);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        mapManager = new MapManager(this, dif);
        mapManager.getTimeManagementMaster().stop();
        mapManager.generateButtons();
        mapManager.generateMap();
    }

    @Override
    protected void onDestroy() {
        mapManager.getTimeManagementMaster().stop();
        super.onDestroy();
    }
}