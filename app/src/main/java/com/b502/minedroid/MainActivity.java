package com.b502.minedroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.b502.minedroid.utils.MapManager;
import com.b502.minedroid.utils.RecordItem;
import com.b502.minedroid.utils.SqlHelper;
import com.b502.minedroid.utils.TimeManagementMaster;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    static  int num = 0 ;
    private TextView titMain;
    private Button btneasy;
    private Button btnmiddle;
    private Button btnhard;
    private Button btnrecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)actionBar.hide();

        titMain = (TextView) findViewById(R.id.titMain);
        btneasy = (Button) findViewById(R.id.btneasy);
        btnmiddle = (Button) findViewById(R.id.btnmiddle);
        btnhard = (Button) findViewById(R.id.btnhard);
        btnrecord = (Button) findViewById(R.id.btnrecord);

        btneasy.setOnClickListener(this);
        btnmiddle.setOnClickListener(this);
        btnhard.setOnClickListener(this);
        btnrecord.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i ;
        switch (view.getId())
        {
            case R.id.btneasy:
                i = new Intent(MainActivity.this,gameActivity.class);
                i.putExtra("diff", MapManager.GameDifficulty.EASY.ordinal());
                startActivity(i);
                break;
            case R.id.btnmiddle:
                i = new Intent(this,gameActivity.class);
                i.putExtra("diff", MapManager.GameDifficulty.MIDDLE.ordinal());
                startActivity(i);
                break;
            case R.id.btnhard:
                i = new Intent(this,gameActivity.class);
                i.putExtra("diff", MapManager.GameDifficulty.HARD.ordinal());
                startActivity(i);
                break;
            case R.id.btnrecord:
                i = new Intent(this,ToplistActivity.class);
//                i.putExtra("diff", MapManager.GameDifficulty.HARD.ordinal());
                startActivity(i);
                break;
            default:
                break;
        }
    }
}