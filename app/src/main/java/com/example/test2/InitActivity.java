package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.Toast;

public class InitActivity extends AppCompatActivity {
    public String dataText1, dataText2, dataText3, dataText4, dataText5, dataText6, dataText7, key;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        Button button = findViewById(R.id.button);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        dataText1 = sharedPreferences.getString("DID_1", "");
        dataText2 = sharedPreferences.getString("DID_2", "");
        dataText3 = sharedPreferences.getString("DID_3", "");
        dataText4 = sharedPreferences.getString("DID_4", "");
        dataText5 = sharedPreferences.getString("DID_5", "");
        dataText6 = sharedPreferences.getString("DID_6", "");
        dataText7 = sharedPreferences.getString("DID_7", "");
        key = sharedPreferences.getString("key1", "");

        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Choice_Roll.class);
            startActivity(intent);
        });

        button1.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Set_Information.class);
            startActivity(intent);
        });

        button2.setOnClickListener(view -> {
            if (isSharedPreferencesDataAvailable()) {
                Intent intent = new Intent(getApplicationContext(), My_DID.class);
                intent.putExtra("text1", dataText1);
                intent.putExtra("text2", dataText2);
                intent.putExtra("text3", dataText3);
                intent.putExtra("text4", dataText4);
                intent.putExtra("text5", dataText5);
                intent.putExtra("text6", dataText6);
                intent.putExtra("text7", key);
                intent.putExtra("hyperlink", dataText7);
                startActivity(intent);
            } else {
                Toast.makeText(this, "발급된 VC가 없습니다.\nVC를 생성해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isSharedPreferencesDataAvailable() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        dataText1 = sharedPreferences.getString("DID_1", "");
        dataText2 = sharedPreferences.getString("DID_2", "");
        dataText3 = sharedPreferences.getString("DID_3", "");
        dataText4 = sharedPreferences.getString("DID_4", "");
        dataText5 = sharedPreferences.getString("DID_5", "");
        dataText6 = sharedPreferences.getString("DID_6", "");
        dataText7 = sharedPreferences.getString("DID_7", "");
        key = sharedPreferences.getString("key1", "");

        return !(dataText1.isEmpty() || dataText2.isEmpty() || dataText3.isEmpty() ||
                dataText4.isEmpty() || dataText5.isEmpty() || dataText6.isEmpty() || dataText7.isEmpty() || key.isEmpty());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("DID_1", dataText1);
        editor.putString("DID_2", dataText2);
        editor.putString("DID_3", dataText3);
        editor.putString("DID_4", dataText4);
        editor.putString("DID_5", dataText5);
        editor.putString("DID_6", dataText6);
        editor.putString("DID_7", dataText7);
        editor.putString("key1", key);
        editor.apply();
    }
}