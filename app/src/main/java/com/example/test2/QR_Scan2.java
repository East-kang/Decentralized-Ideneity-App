package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QR_Scan2 extends AppCompatActivity {
    private IntentIntegrator qrScan;
    String[] DID;
    public String text1,  text2, text3, text4, text5, text6, text7, hyperlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan2);

        Intent intent = getIntent();
        String challenge = intent.getExtras().getString("challenge");
        text1 = intent.getExtras().getString("text1");
        text2 = intent.getExtras().getString("text2");
        text3 = intent.getExtras().getString("text3");
        text4 = intent.getExtras().getString("text4");
        text5 = intent.getExtras().getString("text5");
        text6 = intent.getExtras().getString("text6");
        text7 = intent.getExtras().getString("text7");
        hyperlink = intent.getExtras().getString("hyperlink");

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
                DID = result.getContents().split("\\n");
                Intent intent = new Intent(getApplicationContext(), My_DID.class);
                intent.putExtra("DID1", DID[0]);
                intent.putExtra("DID2", DID[1]);
                intent.putExtra("DID3", DID[2]);
                intent.putExtra("DID4", DID[3]);
                intent.putExtra("DID5", DID[4]);
                intent.putExtra("DID6", DID[5]);
                intent.putExtra("DID7", DID[6]);
                intent.putExtra("key", text7);

                Toast.makeText(this,"DID가 발급 되었습니다.", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}