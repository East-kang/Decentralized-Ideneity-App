package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Set_Information extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_information);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        EditText text1 = findViewById(R.id.text1);
        EditText text2 = findViewById(R.id.text2);
        EditText text3 = findViewById(R.id.text3);
        EditText text4 = findViewById(R.id.text4);
        EditText text5 = findViewById(R.id.text5);
        EditText text6 = findViewById(R.id.text6);
        EditText text7 = findViewById(R.id.text7);
        Button button = findViewById(R.id.button);
        Button btn_erase = findViewById(R.id.btn_erase);

        String text1Value = sharedPreferences.getString("text1", "");
        String text2Value = sharedPreferences.getString("text2", "");
        String text3Value = sharedPreferences.getString("text3", "");
        String text4Value = sharedPreferences.getString("text4", "");
        String text5Value = sharedPreferences.getString("text5", "");
        String text6Value = sharedPreferences.getString("text6", "");
        String text7Value = sharedPreferences.getString("text7", "");

        text1.setText(text1Value);  text2.setText(text2Value);  text3.setText(text3Value);
        text4.setText(text4Value);  text5.setText(text5Value);  text6.setText(text6Value);
        text7.setText(text7Value);

        btn_erase.setOnClickListener(view -> {
            text1.setText("");      text2.setText("");      text3.setText("");      text4.setText("");
            text5.setText("");      text6.setText("");      text7.setText("");

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("text1", "");
            editor.putString("text2", "");
            editor.putString("text3", "");
            editor.putString("text4", "");
            editor.putString("text5", "");
            editor.putString("text6", "");
            editor.putString("text7", "");
            editor.apply();

        });

        button.setOnClickListener(view -> {
            String enteredtext1Value = text1.getText().toString();      // 입력 값 저장 변수
            String enteredtext2Value = text2.getText().toString();
            String enteredtext3Value = text3.getText().toString();
            String enteredtext4Value = text4.getText().toString();
            String enteredtext5Value = text5.getText().toString();
            String enteredtext6Value = text6.getText().toString();
            String enteredtext7Value = text7.getText().toString();

            if (enteredtext1Value.isEmpty() || enteredtext2Value.isEmpty() || enteredtext3Value.isEmpty()
                    || enteredtext4Value.isEmpty() || enteredtext5Value.isEmpty() || enteredtext6Value.isEmpty()) {
                Toast toast = Toast.makeText(getApplicationContext(), "모든 데이터를 입력해주세요.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0); // Toast 위치를 화면 중앙으로 변경
                toast.show();
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("text1", enteredtext1Value);
                editor.putString("text2", enteredtext2Value);
                editor.putString("text3", enteredtext3Value);
                editor.putString("text4", enteredtext4Value);
                editor.putString("text5", enteredtext5Value);
                editor.putString("text6", enteredtext6Value);
                editor.putString("text7", enteredtext7Value);
                editor.apply();

                Intent intent = new Intent(Set_Information.this, FaceModel.class);
                intent.putExtra("text1", enteredtext1Value);
                intent.putExtra("text2", enteredtext2Value);
                intent.putExtra("text3", enteredtext3Value);
                intent.putExtra("text4", enteredtext4Value);
                intent.putExtra("text5", enteredtext5Value);
                intent.putExtra("text6", enteredtext6Value);
                intent.putExtra("text7", enteredtext7Value);
                startActivity(intent);
            }
        });
    }
}