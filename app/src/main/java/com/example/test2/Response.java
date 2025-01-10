package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Response extends AppCompatActivity {

    public static String secretKey;
    public static byte[] ivBytes;
    public String text1,  text2, text3, text4, text5, text6, text7, hyperlink;

    //AES256 암호화
    public static String aesEncode(String str) {
        try {
            byte[] textBytes = str.getBytes("UTF-8");
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);

            return Base64.encodeToString(cipher.doFinal(textBytes), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        ImageView qrcode = findViewById(R.id.qr_code);
        TextView textView = findViewById(R.id.text);

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

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(challenge, BarcodeFormat.QR_CODE, 300, 300);

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

            qrcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        textView.setText(challenge);

        button1.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), VC_Request.class);
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

        button2.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), QR_Scan2.class);
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