package com.shang.android_hw6;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class NoteEditor extends AppCompatActivity {

    EditText titleEdit,bodyEdit;
    Intent intent;
    SQLiteDatabase db;
    DBopneHelper dBopneHelper;
    int mode=0;
    String orTitle,orBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        titleEdit=(EditText)findViewById(R.id.titleEdit);
        bodyEdit=(EditText)findViewById(R.id.bodyEdit);

        intent=getIntent();
        titleEdit.setText(intent.getStringExtra(MainActivity.TITLE));
        bodyEdit.setText(intent.getStringExtra(MainActivity.BODY));
        orTitle=intent.getStringExtra(MainActivity.TITLE);
        orBody=intent.getStringExtra(MainActivity.BODY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        String title=titleEdit.getText().toString();
        String body=bodyEdit.getText().toString();
        mode=intent.getIntExtra(MainActivity.MODE,1);
        dBopneHelper=new DBopneHelper(this);
        db=dBopneHelper.getWritableDatabase();
        if(mode==1){
            ContentValues cv=new ContentValues();
            cv.put("title",title);
            cv.put("body",body);
            db.insert(MainActivity.DATABASE_TABLE,null,cv);
        }else if(mode==2){
            ContentValues cv=new ContentValues();
            cv.put("title",title);
            db.update(MainActivity.DATABASE_TABLE,cv,"title='"+orTitle+"'",null);
            cv.clear();
            cv.put("body",body);
            db.update(MainActivity.DATABASE_TABLE,cv,"body='"+orBody+"'",null);
        }
        db.close();
    }
}
