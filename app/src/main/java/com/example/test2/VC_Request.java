package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class VC_Request extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vc_request);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        ImageView imageView = findViewById(R.id.qr_code);

        Intent intent = getIntent();
        String text1 = intent.getExtras().getString("text1");
        String text2 = intent.getExtras().getString("text2");
        String text3 = intent.getExtras().getString("text3");
        String text4 = intent.getExtras().getString("text4");
        String text5 = intent.getExtras().getString("text5");
        String text6 = intent.getExtras().getString("text6");
        String text7 = intent.getExtras().getString("text7");
        String hyperlink = intent.getExtras().getString("hyperlink");
        // QR 코드 생성
        String qrContent = text1 + "\n" + text2 + "\n" + text3 + "\n" + text4 + "\n" + text5 + "\n" + text6 + "\n" + hyperlink + "\n" + text7;

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(qrContent, BarcodeFormat.QR_CODE, 300, 300);

            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            int[] pixels = new int[width * height];

            // BitMatrix에서 픽셀 배열로 변환
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF;
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }


        button1.setOnClickListener(view -> {
            Intent intent1 = new Intent(getApplicationContext(), SignPad.class);
            intent1.putExtra("text1", text1);
            intent1.putExtra("text2", text2);
            intent1.putExtra("text3", text3);
            intent1.putExtra("text4", text4);
            intent1.putExtra("text5", text5);
            intent1.putExtra("text6", text6);
            intent1.putExtra("text7", text7);
            intent1.putExtra("hyperlink", hyperlink);
            startActivity(intent1);
        });

        button2.setOnClickListener(view -> {
            Intent intent1 = new Intent(getApplicationContext(), QR_Scan.class);
            intent1.putExtra("text1", text1);
            intent1.putExtra("text2", text2);
            intent1.putExtra("text3", text3);
            intent1.putExtra("text4", text4);
            intent1.putExtra("text5", text5);
            intent1.putExtra("text6", text6);
            intent1.putExtra("text7", text7);
            intent1.putExtra("hyperlink", hyperlink);
            startActivity(intent1);
        });
    }
}