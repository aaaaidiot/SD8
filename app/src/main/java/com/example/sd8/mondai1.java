package com.example.sd8;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class mondai1 extends AppCompatActivity implements View.OnClickListener {

    private Timer timer = new Timer();
    private EditText edit = null;
    private  TextView Tim =null;
    private TimerTask timerTask1;
    private  String  tims;

    private final Handler handler = new Handler(Looper.getMainLooper());

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            count ++;
            Tim.setText(dataFormat.
                    format(count*period));
            handler.postDelayed(this, period);
        }
    };

    private final SimpleDateFormat dataFormat =
            new SimpleDateFormat("mm:ss", Locale.US);

    private int count, period;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mondai1);
         Tim  = findViewById(R.id.timer);
        TextView textView = findViewById(R.id.mondai);

        count = 0;
        period = 1000;

        Tim = findViewById(R.id.timer);
        Tim.setText(dataFormat.format(0));
        handler.post(runnable);

        // テキストを設定
        // strings.xmlに書き込んだテキストを表示
        textView.setText(R.string.text);

        //editテキストの取得
        edit = (EditText) findViewById(R.id.input);
        Button sendbotton = (Button) findViewById(R.id.send);
        sendbotton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        handler.removeCallbacks(runnable);
        Tim.setText(dataFormat.format(0));
        tims=Integer.toString(count);
        count = 0;

        // インテントへのインスタンス生成
        Intent intent = new Intent(mondai1.this, k_result.class);
        //　インテントに値をセット
        intent.putExtra("keyword", edit.getText().toString());
        intent.putExtra("time",tims);
        //採点画面へ
        startActivity(intent);
    }


}