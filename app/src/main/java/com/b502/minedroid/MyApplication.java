package com.b502.minedroid;

import android.app.Application;

import com.b502.minedroid.utils.SqlHelper;

public class MyApplication extends Application {
    public static MyApplication Instance;
    public SqlHelper sqlHelper;

    public MyApplication() {
        super();
        sqlHelper = new SqlHelper(this, "records.db", null, 1);
        Instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
