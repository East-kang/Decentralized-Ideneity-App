package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class Sub_VP extends AppCompatActivity {

    TextView textView1, textView2, textView3;
    Button button, button1, button2, button3, button4;
    EditText editText;
    public String name, gender, birth, criminal, national, academy, hyperlink, key;
    public String VP, text;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("text");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_vp);

        textView1 = (TextView) findViewById(R.id.textView2);
        textView2 = (TextView) findViewById(R.id.textView4);
        textView3 = (TextView) findViewById(R.id.textView6);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        editText.setOnLongClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            if (clipboard != null && clipboard.hasPrimaryClip()) {
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                String pasteText = item.getText().toString();
                editText.setText(pasteText);
                return true; // 클립보드 붙여넣기 동작을 처리했으므로 true 반환
            }
            return false; // 클립보드가 비어있거나 오류가 있을 경우 false 반환
        });

        Intent intent = getIntent();
        name = intent.getExtras().getString("DID1");
        gender = intent.getExtras().getString("DID2");
        birth = intent.getExtras().getString("DID3");
        criminal = intent.getExtras().getString("DID4");
        national = intent.getExtras().getString("DID5");
        academy = intent.getExtras().getString("DID6");
        hyperlink = intent.getExtras().getString("DID7");
        key = intent.getExtras().getString("key");
        VP = intent.getExtras().getString("VP");

        textView1.setText(VP);
        textView3.setText("'VP 제출' 버튼을 눌러 VP를 제출 및 확인 하세요.");
    }

    @Override
    protected void onStart() {
        super.onStart();

        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                text = dataSnapshot.getValue(String.class);

                if (text.contains("1 Klay 전송 중")){
                    textView3.setText("Kaikas 앱의 klaytnfinder를 통해 Verifier의 Key를 아래에 입력한 뒤 'Key 인증' 버튼을 누르세요.");
                }

                if (text.contains("Key 인증 재요청"))
                    textView3.setText("Verifier의 Key를 아래에 맞게 다시 입력한 뒤 'Key 인증' 버튼을 누르세요.");

                if (text.contains("Holder Key 인증 과정"))
                    textView3.setText("Klaytnfinder에서 Verifier의 Key를 확인하고 아래에 입력하세요.\n'Key 인증' 버튼을 눌러 인증하세요.");

                if (text.contains("Holder Key 인증 완료"))
                    textView3.setText("'서비스 이용' 버튼을 눌러 서비스를 이용해주세요.");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), VP.class);
            intent.putExtra("DID1", name);
            intent.putExtra("DID2", gender);
            intent.putExtra("DID3", birth);
            intent.putExtra("DID4", criminal);
            intent.putExtra("DID5", national);
            intent.putExtra("DID6", academy);
            intent.putExtra("DID7", hyperlink);
            intent.putExtra("key", key);
            startActivity(intent);
        });

        button1.setOnClickListener(view -> {
            textView2.setText(VP);
            conditionRef.setValue(textView2.getText().toString());
            Toast.makeText(this, "VP 제출 완료", Toast.LENGTH_SHORT).show();
        });

        button2.setOnClickListener(view -> {
            if (!editText.getText().toString().isEmpty()) {
                if ((text.contains("Holder Key 인증 과정")) || (text.contains("Key 인증 재요청"))) {
                    textView3.setText("Verifier Key 보내기 성공!\n서비스를 이용해주세요!!");
                    conditionRef.setValue("▼ Verifier Key 확인 ▼\n" + editText.getText().toString());
                } else
                    Toast.makeText(this, "Key 인증 단계가 아닙니다.", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Verifier의 Key를 입력하세요.", Toast.LENGTH_SHORT).show();
        });

        button3.setOnClickListener(view -> {
            if (text.contains("Holder Key 인증 완료"))
                Toast.makeText(this, "서비스 이용 가능", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "VP를 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
        });

        button4.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), InitActivity.class);
            intent.putExtra("PublicKey", key);
            startActivity(intent);
        });
    }
}