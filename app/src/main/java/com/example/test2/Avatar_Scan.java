package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Avatar_Scan extends AppCompatActivity {

    private IntentIntegrator qrScan;
    String text1, text2, text3, text4, text5, text6, text7, hyperlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_scan);

        Intent intent = getIntent();
        text1 = intent.getExtras().getString("text1");
        text2 = intent.getExtras().getString("text2");
        text3 = intent.getExtras().getString("text3");
        text4 = intent.getExtras().getString("text4");
        text5 = intent.getExtras().getString("text5");
        text6 = intent.getExtras().getString("text6");
        text7 = intent.getExtras().getString("text7");

        qrScan = new IntentIntegrator(this);
        qrScan.setPrompt("QR코드를 사각형 안에 넣어주세요.");
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                // todo
            } else {
                hyperlink = result.getContents();
                Intent intent = new Intent(getApplicationContext(), SignPad.class);
                intent.putExtra("text1", text1);
                intent.putExtra("text2", text2);
                intent.putExtra("text3", text3);
                intent.putExtra("text4", text4);
                intent.putExtra("text5", text5);
                intent.putExtra("text6", text6);
                intent.putExtra("text7", text7);
                intent.putExtra("hyperlink", hyperlink);
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}