package com.b502.minedroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.b502.minedroid.utils.MapManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btneasy;
    private Button btnmiddle;
    private Button btnhard;
    private Button btnrecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btneasy = findViewById(R.id.btneasy);
        btnmiddle = findViewById(R.id.btnmiddle);
        btnhard = findViewById(R.id.btnhard);
        btnrecord = findViewById(R.id.btnrecord);

        btneasy.setOnClickListener(this);
        btnmiddle.setOnClickListener(this);
        btnhard.setOnClickListener(this);
        btnrecord.setOnClickListener(this);

        //Toast.makeText(this,"hahaha", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btneasy:
                i = new Intent(this, GameActivity.class);
                i.putExtra("diff", MapManager.GameDifficulty.EASY.ordinal());
                startActivity(i);
                break;
            case R.id.btnmiddle:
                i = new Intent(this, GameActivity.class);
                i.putExtra("diff", MapManager.GameDifficulty.MIDDLE.ordinal());
                startActivity(i);
                break;
            case R.id.btnhard:
                i = new Intent(this, GameActivity.class);
                i.putExtra("diff", MapManager.GameDifficulty.HARD.ordinal());
                startActivity(i);
                break;
            case R.id.btnrecord:
                i = new Intent(this, ToplistActivity.class);
//                i.putExtra("diff", MapManager.GameDifficulty.HARD.ordinal());
                startActivity(i);
                break;
            default:
                break;
        }
    }
}