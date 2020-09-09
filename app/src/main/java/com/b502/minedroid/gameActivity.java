package com.b502.minedroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.b502.minedroid.utils.MapManager;
import com.b502.minedroid.utils.RecordItem;
import com.b502.minedroid.utils.SqlHelper;
import com.b502.minedroid.utils.TimeManagementMaster;

import java.util.List;

public class gameActivity extends AppCompatActivity {

    private TextView txtTitle;
    private ImageButton btnBack;
    private Button btnSmile;
    private MapManager mapManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent i = getIntent();
        final MapManager.GameDifficulty dif = MapManager.GameDifficulty.values()[i.getIntExtra("diff", 0)];
        //隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();

        txtTitle = findViewById(R.id.titMain);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnSmile = (Button) findViewById(R.id.btnsmile);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapManager.getTimeManagementMaster().stop();
                mapManager = new MapManager(gameActivity.this, dif);
                mapManager.generateButtons();
                mapManager.generateMap();


            }
        });

        mapManager = new MapManager(this, dif);
        mapManager.generateButtons();
        mapManager.generateMap();
    }
}