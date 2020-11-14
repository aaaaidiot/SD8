package com.example.sd8;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.TextView;

public class mondai1 extends AppCompatActivity {

    private EditText edit = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mondai1);

        // text_view： activity_main.xml の TextView の id

        TextView textView = findViewById(R.id.mondai);

        // テキストを設定
        // strings.xmlに書き込んだテキストを表示
        textView.setText(R.string.text);


        //editテキストの取得
        edit = (EditText)findViewById(R.id.input);

        Button sendbotton = (Button)findViewById(R.id.send);

        sendbotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // インテントへのインスタンス生成
                Intent intent = new Intent(mondai1.this, k_result.class);
                //　インテントに値をセット
                intent.putExtra("keyword", edit.getText().toString());
                //採点画面へ
                startActivity(intent);
            }
        });

        }
}
