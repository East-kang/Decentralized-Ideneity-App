package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class FaceModel extends AppCompatActivity {
    VideoView videoView;
    TextView text;
    Button button1, button2;
    public String Uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_model);

        videoView = findViewById(R.id.videoView);
        text = findViewById(R.id.text);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        Intent intent = getIntent();
        String text1 = intent.getExtras().getString("text1");
        String text2 = intent.getExtras().getString("text2");
        String text3 = intent.getExtras().getString("text3");
        String text4 = intent.getExtras().getString("text4");
        String text5 = intent.getExtras().getString("text5");
        String text6 = intent.getExtras().getString("text6");
        String text7 = intent.getExtras().getString("text7");

        button1.setOnClickListener(view -> {
            Intent intent1 = new Intent(getApplicationContext(), Set_Information.class);
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
            Intent intent1 = new Intent(getApplicationContext(), BodySize.class);
            intent1.putExtra("Uri", Uri);
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

    public void button(View view) {    // 동영상 선택 누르면 실행됨 동영상 고를 갤러리 오픈
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // 갤러리
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                MediaController mc = new MediaController(this); // 비디오 컨트롤 가능하게(일시정지, 재시작 등)
                videoView.setMediaController(mc);

                Uri fileUri = data.getData();
                videoView.setVideoPath(String.valueOf(fileUri));    // 선택한 비디오 경로 비디오뷰에 셋
                videoView.start();  // 비디오뷰 시작
                Uri = String.valueOf(fileUri);
                text.setText(Uri);
            }
        }
    }
}