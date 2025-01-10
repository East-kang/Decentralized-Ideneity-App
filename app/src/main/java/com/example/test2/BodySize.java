package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

public class BodySize extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_size);
        EditText text8 = findViewById(R.id.text8);      EditText text9 = findViewById(R.id.text9);
        EditText text10 = findViewById(R.id.text10);    EditText text11 = findViewById(R.id.text11);
        TextView textView = findViewById(R.id.textView1);
        TextView hyperlink = findViewById(R.id.textView4);
        Button button = findViewById(R.id.button);    Button button1 = findViewById(R.id.button1);    Button button2 = findViewById(R.id.button2);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String text8Value = sharedPreferences.getString("text8", "");
        String text9Value = sharedPreferences.getString("text9", "");
        String text10Value = sharedPreferences.getString("text10", "");
        String text11Value = sharedPreferences.getString("text11", "");

        text8.setText(text8Value);      text9.setText(text9Value);
        text10.setText(text10Value);    text11.setText(text11Value);

        Intent intent = getIntent();
        String text1 = intent.getExtras().getString("text1");
        String text2 = intent.getExtras().getString("text2");
        String text3 = intent.getExtras().getString("text3");
        String text4 = intent.getExtras().getString("text4");
        String text5 = intent.getExtras().getString("text5");
        String text6 = intent.getExtras().getString("text6");
        String text7 = intent.getExtras().getString("text7");
        String Uri = intent.getExtras().getString("Uri");

        textView.setText("머리 모델 영상 ▼\n" + Uri);

        button.setOnClickListener(view -> {
            String enteredtext8Value = text8.getText().toString();      // 입력 값 저장 변수
            String enteredtext9Value = text9.getText().toString();
            String enteredtext10Value = text10.getText().toString();
            String enteredtext11Value = text11.getText().toString();

            if (enteredtext8Value.isEmpty() || enteredtext9Value.isEmpty() || enteredtext10Value.isEmpty()
                    || enteredtext11Value.isEmpty() || textView.getText().toString().isEmpty()) {
                Toast.makeText(this, "모든 데이터를 입력해주세요", Toast.LENGTH_SHORT).show();
            } else {
                hyperlink.setText("https://ipfs.io/ipfs/QmahYK8ngx9e4ahpdm2U5bysgvD8jK8N2MsTe1eWJvy4vT");
            }
        });

        button1.setOnClickListener(view -> {
            String enteredtext8Value = text8.getText().toString();      // 입력 값 저장 변수
            String enteredtext9Value = text9.getText().toString();
            String enteredtext10Value = text10.getText().toString();
            String enteredtext11Value = text11.getText().toString();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("text8", enteredtext8Value);
            editor.putString("text9", enteredtext9Value);
            editor.putString("text10", enteredtext10Value);
            editor.putString("text11", enteredtext11Value);
            editor.apply();

            Intent intent1 = new Intent(getApplicationContext(), FaceModel.class);
            intent1.putExtra("text1", text1);
            intent1.putExtra("text2", text2);
            intent1.putExtra("text3", text3);
            intent1.putExtra("text4", text4);
            intent1.putExtra("text5", text5);
            intent1.putExtra("text6", text6);
            intent1.putExtra("text7", text7);
            startActivity(intent1);
        });

        button2.setOnClickListener(view -> {
            String enteredtext8Value = text8.getText().toString();      // 입력 값 저장 변수
            String enteredtext9Value = text9.getText().toString();
            String enteredtext10Value = text10.getText().toString();
            String enteredtext11Value = text11.getText().toString();

            if (enteredtext8Value.isEmpty() || enteredtext9Value.isEmpty() || enteredtext10Value.isEmpty()
                    || enteredtext11Value.isEmpty() || textView.getText().toString().isEmpty() || hyperlink.getText().toString().isEmpty()) {
                Toast toast = Toast.makeText(getApplicationContext(), "하이퍼링크를 발급 받지 못했습니다.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("text8", enteredtext8Value);
                editor.putString("text9", enteredtext9Value);
                editor.putString("text10", enteredtext10Value);
                editor.putString("text11", enteredtext11Value);
                editor.apply();

                Intent intent2 = new Intent(getApplicationContext(), SignPad.class);
                intent2.putExtra("text1", text1);
                intent2.putExtra("text2", text2);
                intent2.putExtra("text3", text3);
                intent2.putExtra("text4", text4);
                intent2.putExtra("text5", text5);
                intent2.putExtra("text6", text6);
                intent2.putExtra("text7", text7);
                intent2.putExtra("Uri", Uri);
                intent2.putExtra("hyperlink", hyperlink.getText().toString());
                startActivity(intent2);
            }
        });
    }
}