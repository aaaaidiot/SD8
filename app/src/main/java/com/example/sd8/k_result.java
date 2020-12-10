package com.example.sd8;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;

public class k_result extends AppCompatActivity {

    private UploadTask task;
    private saveDBHelper helper;
    private SQLiteDatabase db;
    private TextView tv;
    private  TextView timeshow;
    private  String sec;



    Button btnSave;
    Button btnShow;
    Button btnPost;
    Button btnBuck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_result);
        TextView text = (TextView) findViewById(R.id.textcatch);
        timeshow = findViewById(R.id.time);


        btnSave = findViewById(R.id.save);
        btnShow = findViewById(R.id.show);
        btnPost = findViewById(R.id.bpost);
        btnBuck = findViewById(R.id.buck);

        final String url = "http://10.0.2.2/k_result.php";


        // インテントを取得
        Intent intent = getIntent();        // インテントに保存されたデータを取得
        final String data = intent.getStringExtra("keyword");
        final String ti = intent.getStringExtra("time");
        text.setText(data);

        //タイマーの処理
        int time = Integer.valueOf(ti).intValue();
        int min = 0;
        min = (time % 3600) / 60;
        time = time % 60;
        timeshow.setText(String.valueOf(min) + "分" + String.valueOf(time) + "秒" + "かかりました。");
        sec = String.valueOf(time);


        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //非同期処理
                String param0 = data;

                if (param0.length() != 0) {
                    task = new UploadTask();
                    task.setListener(createListener());
                    task.execute(param0);
                }
            }
        });

        //ブラウザ起動
        Button browser = findViewById(R.id.browser);
        browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // phpで作成されたhtmlファイルへアクセス
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                // text clear
                tv.setText("");
            }
        });

        tv = findViewById(R.id.post);


        //保存ボタンが押されたらDBに保存
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast toast = Toast.makeText(k_result.this, "保存しました。", Toast.LENGTH_LONG);
                toast.show();

                if (helper == null) {
                    helper = new saveDBHelper(getApplicationContext());
                }

                if (db == null) {
                    db = helper.getWritableDatabase();
                }

                ContentValues values = new ContentValues();
                values.put(saveDB.FeedEntry.COLUMN_NAME_WORD, data);
                values.put(saveDB.FeedEntry.COLUMN_NAME_TENSU, sec);
                long newRowId = db.insert(saveDB.FeedEntry.TABLE_NAME, null, values);
                Log.d("newRowId", Long.toString(newRowId));
            }
        });

        //これまでのデータを見るボタンが押されたら
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //readDataに飛ぶ
                Intent intent = new Intent(k_result.this, readData.class);
                startActivity(intent);
            }
        });

        btnBuck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(k_result.this, japanese.class);
                startActivity(intent);
            }
        });
    }

    //POSTされたか表示される
    @Override
    protected void onDestroy() {
        task.setListener(null);
        super.onDestroy();
    }

    private UploadTask.Listener createListener() {

        return new UploadTask.Listener() {
            @Override
            public void onSuccess(String result) {
                Toast t = Toast.makeText(k_result.this, result, Toast.LENGTH_LONG);
                t.show();
            }
        };
    }

}

