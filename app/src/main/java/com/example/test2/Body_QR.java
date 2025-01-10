package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class Body_QR extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_qr);

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
        String text8 = intent.getExtras().getString("text8");
        String text9 = intent.getExtras().getString("text9");
        String text10 = intent.getExtras().getString("text10");
        String text11 = intent.getExtras().getString("text11");

        String qrContent = text8 + "\n" + text9 + "\n" + text10 + "\n" + text11;

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
            Intent intent1 = new Intent(getApplicationContext(), BodySize.class);
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
            Intent intent1 = new Intent(getApplicationContext(), Avatar_Scan.class);
            intent1.putExtra("text1", text1);
            intent1.putExtra("text2", text2);
            intent1.putExtra("text3", text3);
            intent1.putExtra("text4", text4);
            intent1.putExtra("text5", text5);
            intent1.putExtra("text6", text6);
            intent1.putExtra("text7", text7);
            startActivity(intent1);
        });
    }
}