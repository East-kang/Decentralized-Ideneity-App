package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class My_DID extends AppCompatActivity {
    public SharedPreferences sharedPreferences;
    private String name, gender, birth, criminal, national, academy, hyperlink;
    private String data1, data2, data3, data4, data5, data6, data7, key, key1;
    public boolean erase = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_did);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        TextView text2 = findViewById(R.id.text2);      TextView text4 = findViewById(R.id.text4);
        TextView text6 = findViewById(R.id.text6);      TextView text8 = findViewById(R.id.text8);
        TextView text10 = findViewById(R.id.text10);    TextView text12 = findViewById(R.id.text12);
        TextView text14 = findViewById(R.id.text14);

        Button btn_erase = findViewById(R.id.erase);    Button button1 = findViewById(R.id.button1);    Button button2 = findViewById(R.id.button2);

        Intent intent = getIntent();
        name = intent.getExtras().getString("text1");
        gender = intent.getExtras().getString("text2");
        birth = intent.getExtras().getString("text3");
        criminal = intent.getExtras().getString("text4");
        national = intent.getExtras().getString("text5");
        academy = intent.getExtras().getString("text6");
        key = intent.getExtras().getString("text7");
        hyperlink = intent.getExtras().getString("hyperlink");

        data1 = sharedPreferences.getString("DID_1", "");   data2 = sharedPreferences.getString("DID_2", "");
        data3 = sharedPreferences.getString("DID_3", "");   data4 = sharedPreferences.getString("DID_4", "");
        data5 = sharedPreferences.getString("DID_5", "");   data6 = sharedPreferences.getString("DID_6", "");
        data7 = sharedPreferences.getString("DID_7", "");   key1 = sharedPreferences.getString("key1", "");

        text2.setText(name);    text4.setText(gender);  text6.setText(birth);   text8.setText(criminal);
        text10.setText(national);   text12.setText(academy);    text14.setText(hyperlink);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("DID_1", name);        editor.putString("DID_2", gender);      editor.putString("DID_3", birth);
        editor.putString("DID_4", criminal);    editor.putString("DID_5", national);    editor.putString("DID_6", academy);
        editor.putString("DID_7", hyperlink);   editor.putString("key1", key);
        editor.apply();
        Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();

        btn_erase.setOnClickListener(view -> {
            erase = true;

            text2.setText("");    text4.setText("");  text6.setText("");   text8.setText("");
            text10.setText("");   text12.setText("");    text14.setText("");

            name = gender = birth = criminal = national = academy = key = hyperlink = "";

            SharedPreferences.Editor editor1 = sharedPreferences.edit();
            editor1.putString("DID_1", "");  editor1.putString("DID_2", "");
            editor1.putString("DID_3", "");  editor1.putString("DID_4", "");
            editor1.putString("DID_5", "");  editor1.putString("DID_6", "");
            editor1.putString("DID_7", "");  editor1.putString("key1", "");
            editor1.apply();
            Toast.makeText(this, "VC가 삭제 되었습니다.\n처음으로 돌아가 VC를 다시 생성해주세요.", Toast.LENGTH_SHORT).show();
        });

        button1.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), InitActivity.class);
            intent1.putExtra("text1", name);        intent1.putExtra("text2", gender);
            intent1.putExtra("text3", birth);       intent1.putExtra("text4", criminal);
            intent1.putExtra("text5", national);    intent1.putExtra("text6", academy);
            intent1.putExtra("text7", key);         intent1.putExtra("hyperlink", hyperlink);
            startActivity(intent1);
        });

        button2.setOnClickListener(v -> {
            if (!erase) {
                Intent intent1 = new Intent(getApplicationContext(), VP.class);
                intent1.putExtra("DID1", name);
                intent1.putExtra("DID2", gender);
                intent1.putExtra("DID3", birth);
                intent1.putExtra("DID4", criminal);
                intent1.putExtra("DID5", national);
                intent1.putExtra("DID6", academy);
                intent1.putExtra("DID7", hyperlink);
                intent1.putExtra("key", key);
                startActivity(intent1);
            } else
                Toast.makeText(this, "사용할 VC가 없습니다.", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("DID_1", data1);
        editor.putString("DID_2", data2);
        editor.putString("DID_3", data3);
        editor.putString("DID_4", data4);
        editor.putString("DID_5", data5);
        editor.putString("DID_6", data6);
        editor.putString("DID_7", data7);
        editor.putString("key1", key1);
        editor.apply();
    }
}
