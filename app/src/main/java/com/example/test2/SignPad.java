package com.example.test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.test2.databinding.ActivityMainBinding;

import java.io.OutputStream;
import java.util.Objects;

public class SignPad extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String text1 = intent.getExtras().getString("text1");
        String text2 = intent.getExtras().getString("text2");
        String text3 = intent.getExtras().getString("text3");
        String text4 = intent.getExtras().getString("text4");
        String text5 = intent.getExtras().getString("text5");
        String text6 = intent.getExtras().getString("text6");
        String text7 = intent.getExtras().getString("text7");
        String Uri = intent.getExtras().getString("Uri");
        String hyperlink = intent.getExtras().getString("hyperlink");


        //Initialize ActivityMainBinding Object Set ContentView As GetRoot()
        mainBinding  = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.button1.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), BodySize.class);
            intent1.putExtra("text1", text1);
            intent1.putExtra("text2", text2);
            intent1.putExtra("text3", text3);
            intent1.putExtra("text4", text4);
            intent1.putExtra("text5", text5);
            intent1.putExtra("text6", text6);
            intent1.putExtra("text7", text7);
            intent1.putExtra("Uri", Uri);
            startActivity(intent1);
        });

        mainBinding.button2.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), My_DID.class);
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

        //Implement the Save Button Code
        mainBinding.btnSave.setOnClickListener(view -> {
            Bitmap bitmap = mainBinding.signatureView.getSignatureBitmap();
            if(bitmap != null)
            {
                mainBinding.imgSignature.setImageBitmap(bitmap);
            }
            else
                Toast.makeText(SignPad.this, "저장할 디지털 서명을 생성 하세요", Toast.LENGTH_SHORT).show();
        });

        //Implement the Clear Button Code
        mainBinding.btnClear.setOnClickListener(view -> {
            mainBinding.signatureView.clearCanvas();
        });

        //Implement the Download Image In Gallery Button Code
        mainBinding.btnDownload.setOnClickListener(view -> {
            mainBinding.imgSignature.buildDrawingCache();
            if(ContextCompat.checkSelfPermission(SignPad.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                saveImage();
            }
            else
            {
                ActivityCompat.requestPermissions(SignPad.this,new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },REQUEST_CODE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE)
        {
            saveImage();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void saveImage(){
        Uri images;
        ContentResolver contentResolver = getContentResolver();
        BitmapDrawable bitmapDrawable = (BitmapDrawable)mainBinding.imgSignature.getDrawable();
        try {
            Bitmap bmp =  bitmapDrawable.getBitmap();
            if(bmp != null)
            {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                {
                    images = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                }
                else
                {
                    images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }

                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.Images.Media.DISPLAY_NAME,"Digital_Sign_" + System.currentTimeMillis() + ".jpg");
                contentValues.put(MediaStore.Images.Media.MIME_TYPE,"images/*");
                Uri uri = contentResolver.insert(images,contentValues);

                try {

                    OutputStream outputStream = contentResolver.openOutputStream(Objects.requireNonNull(uri));
                    bmp.compress(Bitmap.CompressFormat.JPEG,80,outputStream);
                    Objects.requireNonNull(outputStream);

                    Toast.makeText(SignPad.this,"디지털 서명 저장 성공",Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Toast.makeText(SignPad.this,"오류 : " + e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(SignPad.this,"저장할 디지털 서명을 생성 하세요",Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e)
        {
            Toast.makeText(SignPad.this,"저장할 디지털 서명을 생성 하세요",Toast.LENGTH_SHORT).show();
        }
    }
}