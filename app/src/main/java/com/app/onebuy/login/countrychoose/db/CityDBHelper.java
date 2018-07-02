package com.app.onebuy.login.countrychoose.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CityDBHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "citylist";

    /**
     * 创建下载数据库小助手
     * 
     * @param context
     */
    public CityDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 文件下载表
        db.execSQL("create table if not exists citylist_table(_id integer PRIMARY KEY AUTOINCREMENT, ids char, en_name char,cn_name char,"
                + " pinyin_first char,pinyin char,domain char, tel_code char, time_diff char)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        db.execSQL("DROP TABLE IF EXISTS citylist_table;");
        onCreate(db);
    }
}
