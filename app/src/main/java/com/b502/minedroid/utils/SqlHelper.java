package com.b502.minedroid.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SqlHelper extends SQLiteOpenHelper {

    public SqlHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version) {
        super(context, name, cursorFactory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO 创建数据库后，对数据库的操作
        //db= getWritableDatabase();
        db.execSQL("CREATE TABLE easyrecord (_id INTEGER PRIMARY KEY  AUTOINCREMENT, recordtime TEXT, costtime INTEGER);");
        db.execSQL("CREATE TABLE middlerecord (_id INTEGER PRIMARY KEY  AUTOINCREMENT, recordtime TEXT, costtime INTEGER);");
        db.execSQL("CREATE TABLE hardrecord (_id INTEGER PRIMARY KEY  AUTOINCREMENT, recordtime TEXT, costtime INTEGER);");
    }

    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm:ss");
        return sdf.format(calendar.getTime());
    }

    public void addRecord(MapManager.GameDifficulty difficulty, String recordtime, int costtime) {

        SQLiteDatabase db = getWritableDatabase();
        String sqlval = String.format(" (recordtime,costtime) VALUES ('%s',%s)", recordtime, costtime);
        switch (difficulty) {
            case EASY:
                sqlval = "INSERT INTO easyrecord" + sqlval;
                break;

            case MIDDLE:
                sqlval = "INSERT INTO middlerecord" + sqlval;
                break;
            case HARD:
                sqlval = "INSERT INTO hardrecord" + sqlval;
                break;
            default:

        }
        db.execSQL(sqlval);

    }

    public List<RecordItem> getRecords(MapManager.GameDifficulty difficulty) {
        List<RecordItem> ret = new ArrayList<>();
        Cursor result;
        switch (difficulty) {
            case EASY:
                result = getReadableDatabase().rawQuery("SELECT * from easyrecord ORDER BY costtime", null);
                break;
            case MIDDLE:
                result = getReadableDatabase().rawQuery("SELECT * from middlerecord ORDER BY costtime", null);
                break;
            case HARD:
            default:
                result = getReadableDatabase().rawQuery("SELECT * from hardrecord ORDER BY costtime", null);
                break;
        }
        result.moveToFirst();
        while (!result.isAfterLast()) {
            RecordItem r = new RecordItem(result.getString(1), result.getInt(2));
            ret.add(r);
            result.moveToNext();
        }
        result.close();

        return ret;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 更改数据库版本的操作

    }
}
