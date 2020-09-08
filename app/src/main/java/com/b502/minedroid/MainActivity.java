package com.b502.minedroid;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.b502.minedroid.utils.MapManager;
import com.b502.minedroid.utils.TimeManagementMaster;

public class MainActivity extends AppCompatActivity {
    static  int num = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)actionBar.hide();

       final  TextView t = findViewById(R.id.titMain);

                final TimeManagementMaster timeManagementMaster = new TimeManagementMaster(new Handler() {
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);

                        t.setText(Integer.toString(++num));
                    }
                }, 10);
        timeManagementMaster.start();
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!timeManagementMaster.isHangedup())timeManagementMaster.pause();
                else timeManagementMaster.start();
            }
        });
        MapManager mapManager = new MapManager(this,MapManager.GameDifficulty.EASY);

        mapManager.generateButtons();
        mapManager.generateMap();
    }
}