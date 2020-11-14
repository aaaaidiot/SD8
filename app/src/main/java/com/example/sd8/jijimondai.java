package com.example.sd8;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class jijimondai extends AppCompatActivity {

    private TextView countLabel;
    private TextView questionLabel;
    private Button answerBtn1;
    private Button answerBtn2;
    private Button answerBtn3;
    private Button answerBtn4;

    // ここから追加
    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    static final private int QUIZ_COUNT = 10;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    String quizData[][] = {
            // {"都道府県名", "正解", "選択肢１", "選択肢２", "選択肢３"}
            {"2020年2月にEU（欧州連合）を正式離脱した国家は？", "イギリス", "フランス", "ドイツ", "ロシア"},
            {"2020年4月7日に新型コロナウイルスの感染拡大に基づき日本で発令されたが外出自粛・店舗の休業の要請の名称は？", "緊急事態宣言", "国家非常事態宣言", "ソーシャルディスタンス", "ロックダウン"},
            {"2020年5月に警察の不適切な拘束方法によって死亡したジョージ・フロイドさんを機にアメリカを中心に起きた組織的な運動の名称は？", "BLM","BLT", "BL", "BTS"},
            {"史上最年少でプロ入りし、2020年に最年少でタイトルを取得したプロ棋士は？", "藤井壮太", "羽生善治", "加藤一二三", "井山裕太"},
            {"2020年8月14日にベイルートで硝酸アンモニウムの爆発事故が発生した。爆発時の動画が世界的な話題となったが、ベイルートはどこの国の首都か？", "レバノン","ソマリア", "トルコ", "ウズベキスタン"},
            {"2020年の米大統領選で現職のトランプ大統領に対抗する民主党の前副大統領の名前は", "ジョー・バイデン","ジョージ・フロイド", "バラク・オバマ", "ヒラリー・クリントン"},
            {"２０２０年　２０１８年以来２年ぶりに全米オープンで優勝した日本人として話題となった女性プロテニス選手の名前は？", "大坂なおみ", "大阪なおみ", "池江璃花子", "マリア・シャラポワ"},
            {"2020年9月15日に旧立憲民主党と旧国民民主党が合流した新党の名称は？", "立憲民主党", "民進党", "民主党", "自由民主党"},
            {"2020年9月16日に成立した日本の新内閣の総理大臣の名前は", "菅義偉", "菅直人", "安倍晋三", "麻生太郎"},
            {"２０２０年４月２０日の閣議決定された「新型コロナウイルス感染症緊急経済対策」を元に配布された特別定額給付金は", "一人当たり一律10万円", "一人当たり一律5万円", "一人当たり一律100万円", "一人当たり一律20万円"},
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jijimondai);
        // ここから追加
        countLabel = findViewById(R.id.countLabel);
        questionLabel = findViewById(R.id.questionLabel);
        answerBtn1 = findViewById(R.id.answerBtn1);
        answerBtn2 = findViewById(R.id.answerBtn2);
        answerBtn3 = findViewById(R.id.answerBtn3);
        answerBtn4 = findViewById(R.id.answerBtn4);

        // quizDataからクイズ出題用のquizArrayを作成する
        for (int i = 0; i < quizData.length; i++) {

            // 新しいArrayListを準備
            ArrayList<String> tmpArray = new ArrayList<>();

            // クイズデータを追加
            tmpArray.add(quizData[i][0]);  // 都道府県名
            tmpArray.add(quizData[i][1]);  // 正解
            tmpArray.add(quizData[i][2]);  // 選択肢１
            tmpArray.add(quizData[i][3]);  // 選択肢２
            tmpArray.add(quizData[i][4]);  // 選択肢３

            // tmpArrayをquizArrayに追加する
            quizArray.add(tmpArray);
        }

        showNextQuiz();

    }

    public void showNextQuiz() {
        // クイズカウントラベルを更新
        countLabel.setText("Q" + quizCount);

        // ランダムな数字を取得
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        // randomNumを使って、quizArrayからクイズを一つ取り出す
        ArrayList<String> quiz = quizArray.get(randomNum);

        // 問題文（都道府県名）を表示
        questionLabel.setText(quiz.get(0));

        // 正解をrightAnswerにセット
        rightAnswer = quiz.get(1);

        // クイズ配列から問題文（都道府県名）を削除
        quiz.remove(0);

        // 正解と選択肢３つをシャッフル
        Collections.shuffle(quiz);

        // 回答ボタンに正解と選択肢３つを表示
        answerBtn1.setText(quiz.get(0));
        answerBtn2.setText(quiz.get(1));
        answerBtn3.setText(quiz.get(2));
        answerBtn4.setText(quiz.get(3));

        // このクイズをquizArrayから削除
        quizArray.remove(randomNum);
    }


    public void checkAnswer(View view) {

        // どの回答ボタンが押されたか
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        String alertTitle;
        if (btnText.equals(rightAnswer)) {
            alertTitle = "正解!";
            rightAnswerCount++;
        } else {
            alertTitle = "不正解...";
        }

        // ダイアログを作成
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("答え : " + rightAnswer);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (quizCount == QUIZ_COUNT) {
                    // 結果画面へ移動
                    Intent intent = new Intent(getApplicationContext(), jijiresult.class);
                    intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                    startActivity(intent);

                } else {
                    quizCount++;
                    showNextQuiz();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

}

