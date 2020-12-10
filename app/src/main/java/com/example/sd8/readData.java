package com.example.sd8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
public class readData extends AppCompatActivity {

    private saveDBHelper helper;
    private SQLiteDatabase db;

    Button dbdelete;

    //DBの表示のプログラム
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);

        if (helper == null) {
            helper = new saveDBHelper(getApplicationContext());
        }

        if (db == null) {
            db = helper.getWritableDatabase();
        }
        Cursor c = db.query(saveDB.FeedEntry.TABLE_NAME,
                new String[]{
                        saveDB.FeedEntry._ID,
                        saveDB.FeedEntry.COLUMN_NAME_WORD,
                        saveDB.FeedEntry.COLUMN_NAME_TENSU,
                },
                "",
                new String[0],
                "",
                "",
                "");
        List<String> records = new ArrayList<>();
        while (c.moveToNext()) {
            String record =c.getString(0)  + "," +  c.getString(1) +"," +c.getString(2)  + "秒" + "\r\n";
            records.add(record);
        }
        ListView listView = (ListView) findViewById(R.id.dblist);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, records));

        //DBの全件削除
        dbdelete = findViewById(R.id.delete);
        dbdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete("save", null, null);
            }
        });
    }



}