package com.shang.android_hw6;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DBopneHelper dBopneHelper;

    public static String DATABASE_TABLE="demotable";
    public static String DATABASE_CREATETABLE="create table "+DATABASE_TABLE+" (title,body);";
    public static String TITLE="TITLE";
    public static String BODY="BODY";
    public static String MODE="MODE";

    Button button;
    ListView listView;
    ArrayList<String> titleList;
    ArrayList<String> bodyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=(Button)findViewById(R.id.button);
        listView=(ListView)findViewById(R.id.listView);

        dBopneHelper=new DBopneHelper(this);
        db=dBopneHelper.getWritableDatabase();

        button.setOnClickListener(listener);

        search();

        listView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,titleList));
        listView.setOnItemClickListener(listviewListener);
        listView.setOnItemLongClickListener(longClickListener);

        db.close();
    }

    public void search(){
        titleList=new ArrayList();
        bodyList=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from "+DATABASE_TABLE,null);
        String[] name=cursor.getColumnNames();
        for(int i=0;i<name.length;i++){
            Log.d("DATABASE_TEST",cursor.getColumnIndex(name[i])+" "+name[i]);
        }

        cursor.moveToFirst();
        for(int i=0;i<cursor.getCount();i++){
            Log.d("DATABASE_TEST","Title:"+i+":"+cursor.getString(cursor.getColumnIndex(name[0])));
            titleList.add(cursor.getString(cursor.getColumnIndex(name[0])));
            bodyList.add(cursor.getString(cursor.getColumnIndex(name[1])));
            cursor.moveToNext();
        }
    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(MainActivity.this,NoteEditor.class);
            intent.putExtra(MODE,1);
            startActivity(intent);
        }
    };

    AdapterView.OnItemClickListener listviewListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent(MainActivity.this,NoteEditor.class);
            intent.putExtra(TITLE,titleList.get(position));
            intent.putExtra(BODY,bodyList.get(position));
            intent.putExtra(MODE,2);
            startActivity(intent);
        }
    };

    AdapterView.OnItemLongClickListener longClickListener=new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            return false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        db=dBopneHelper.getWritableDatabase();
        search();
        listView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,titleList));
        db.close();
    }
}
