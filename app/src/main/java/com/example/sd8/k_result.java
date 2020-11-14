package com.example.sd8;

import androidx.appcompat.app.AppCompatActivity;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import com.atilika.kuromoji.TokenizerBase;
import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;

public class k_result extends AppCompatActivity {

    private saveDBHelper helper;
    private SQLiteDatabase db;
    private TextView show;

    String str1 = "吾輩";
    String str2 = "猫";
    int ten = 0;

    Button btnSave;
    Button btnShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_result);
        TextView text = (TextView) findViewById(R.id.textcatch);
        TextView result = (TextView) findViewById(R.id.maining);

        btnSave = findViewById(R.id.save);
        btnShow = findViewById(R.id.show);


        // インテントを取得
        Intent intent = getIntent();        // インテントに保存されたデータを取得
        final String data = intent.getStringExtra("keyword");
        text.setText(data);


        if (data.indexOf(str1) != -1) {
            ten = ten + 1;
        }
        if (data.indexOf(str2) != -1) {
            ten = ten + 1;
        }
        final String goukei = String.valueOf(ten);
        result.setText(goukei + "点です。");


        final Tokenizer tokenizer = new Tokenizer.Builder().mode(TokenizerBase.Mode.NORMAL).build();
        List<Token> list = tokenizer.tokenize(data);
        for (Token token : list) {
            Log.v("AddMessage : ", token.getAllFeatures());
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (helper == null) {
                    helper = new saveDBHelper(getApplicationContext());
                }

                if (db == null) {
                    db = helper.getWritableDatabase();
                }

                ContentValues values = new ContentValues();
                values.put(saveDB.FeedEntry.COLUMN_NAME_WORD, data);
                values.put(saveDB.FeedEntry.COLUMN_NAME_TENSU, goukei);
                long newRowId = db.insert(saveDB.FeedEntry.TABLE_NAME, null, values);
                Log.d("newRowId", Long.toString(newRowId));
            }
        });
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readData();
            }
        });
    }

    private void readData() {
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
            String record =c.getString(0)  + "," +  c.getString(1) +"," +c.getString(2)  + "点" + "\r\n";
            records.add(record);
        }
        ListView listView = (ListView) findViewById(R.id.dblist);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, records));
    }
}

