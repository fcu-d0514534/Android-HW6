package com.shang.android_hw6;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shang on 2017/5/3.
 */
public class DBopneHelper extends SQLiteOpenHelper{

    public DBopneHelper(Context context) {
        super(context,"demo.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MainActivity.DATABASE_CREATETABLE);
        ContentValues cv=new ContentValues();
        cv.put("title","Note001");
        cv.put("body","This is a test Note001");
        db.insert(MainActivity.DATABASE_TABLE,null,cv);

        cv=new ContentValues();
        cv.put("title","Note002");
        cv.put("body","This is a test Note002");
        db.insert(MainActivity.DATABASE_TABLE,null,cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
