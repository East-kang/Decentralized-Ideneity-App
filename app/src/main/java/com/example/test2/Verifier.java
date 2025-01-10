package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Verifier extends AppCompatActivity {

    public String text, V_key;
    public String[] key, H_key;
    TextView textView, textView1, textView2;
    Button btn_retry, btn_home;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("text");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifier);

        textView = findViewById(R.id.textView2);
        textView1 = findViewById(R.id.textView3);
        textView2 = findViewById(R.id.textView4);
        btn_retry = findViewById(R.id.btn_retry);
        btn_home = findViewById(R.id.btn_home);

        Intent intent = getIntent();
        V_key = intent.getExtras().getString("PublicKey");

        textView1.setText("수신 받은 VP가 없습니다.\nHolder가 VP를 송신할 때까지 대기 해주세요.");

        btn_home.setOnClickListener(view -> {
            Intent intent1 = new Intent(getApplicationContext(), Choice_Roll.class);
            startActivity(intent1);
        });

        textView2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String str = textView2.getText().toString();
                createClipData(str);
                return false;
            }
        });
    }

    public void createClipData(String message){
        ClipboardManager clipboardManager = (ClipboardManager) getApplicationContext().getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("message", message);

        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                text = dataSnapshot.getValue(String.class);

                if (text.contains("Public Key:")){
                    key = text.split("\\n");
                    H_key = key[0].split(":");
                    textView.setText(text);
                    textView1.setText("Holder의 Key를 통해 Kaikas에서 0.1 Klay를 보내세요.");
                    textView2.setText(H_key[1]);
                    conditionRef.setValue("Holder Key 인증 과정");
                }

                if (text.contains("▼ Verifier Key 확인 ▼")){
                    String[] str = text.split("\\n");
                    if (str[1].toLowerCase().contains(V_key.toLowerCase())) {
                        textView1.setText("Verifier을 통한 Holder Key 인증 완료");
                        conditionRef.setValue("Holder Key 인증 완료");
                    }
                    else {
                        textView1.setText("'Key 인증 재요청' 버튼을 눌러 재요청 하세요.");
                        conditionRef.setValue("Key 인증 실패");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_retry.setOnClickListener(vieew -> {
            if (text.contains("Key 인증 실패")) {
                textView1.setText("Holder에게 Key 인증 재요청 완료");
                conditionRef.setValue("Key 인증 재요청");
            } else
                Toast.makeText(this, "Key 인증 확인 단계가 아닙니다.", Toast.LENGTH_SHORT).show();
        });
    }
}