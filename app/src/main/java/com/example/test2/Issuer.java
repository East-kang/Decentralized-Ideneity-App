package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.nio.charset.StandardCharsets;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Issuer extends AppCompatActivity {

    private TextView textView, textView2;
    private Button btn_send, btn_retry, btn_challenge, btn_check, btn_clear, button1;
    public String[] H_key, response;
    public String I_Key, text;
    public static String secretKey, challenge;
    public static byte[] ivBytes;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("text");

    public static String aesEncode(String str) {
        try {
            byte[] textBytes = str.getBytes(StandardCharsets.UTF_8);
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);

            return Base64.encodeToString(cipher.doFinal(textBytes), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str.toLowerCase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issuer);

        textView = (TextView) findViewById(R.id.textview);
        textView2 = (TextView) findViewById(R.id.textView2);
        btn_retry = (Button) findViewById(R.id.btn_retry);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_challenge = (Button) findViewById(R.id.btn_challenge);
        btn_check = (Button) findViewById(R.id.btn_check);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        button1 = (Button) findViewById(R.id.button1);

        Intent intent = getIntent();
        I_Key = intent.getExtras().getString("PublicKey");

        textView.setText("사용자에게 검증 요청이 오면 'Send 버튼'을 눌러 검증 요청을 확인하세요.");

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

                if (text.contains("▼ Holder 공개 키 ▼"))
                    textView.setText("Holder의 검증 요청 수신\n'Send' 버튼을 눌러 Holder의 Key를 확인하세요.");

                if (text.contains("▼ Issuer Key 확인 ▼")){
                    String[] key = text.split("\\n");
                    if (key[1].toLowerCase().contains(I_Key.toLowerCase())) {
                        textView.setText("Holder Key 인증 완료\n'Challenge 생성' 버튼을 눌러 Challenge를 생성 해주세요.");
                        conditionRef.setValue("Holder Key 인증 완료");
                    }
                    else {
                        textView.setText("'Key 인증 재요청' 버튼을 눌러 재요청 하세요.");
                        conditionRef.setValue("Key 인증 실패");
                    }
                }

                if (text.contains("Response:"))
                    textView.setText("'Response 확인' 버튼을 눌러 Response를 확인하세요.");

                if (text.contains("사용자 검증 완료")) {
                    Toast.makeText(Issuer.this, "사용자 검증 완료", Toast.LENGTH_SHORT).show();
                    conditionRef.setValue("VP 제출");
                    Intent intent = new Intent(getApplicationContext(), Choice_Roll.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_clear.setOnClickListener(view -> {
            textView.setText("사용자에게 검증 요청이 오면 'Send 버튼'을 눌러 검증 요청을 확인하세요.");
            conditionRef.setValue("");
            Toast.makeText(this, "초기화 완료", Toast.LENGTH_SHORT).show();
        });

        btn_send.setOnClickListener(view -> {
            if (text.contains("▼ Holder 공개 키 ▼")){
                textView.setText("아래의 확인된 Holder의 Key를 통해\nKaikas 앱에서 Holder에게 0.1 Klay를 송금하세요.");
                H_key = text.split("\\n");
                textView2.setText(H_key[1]);
                conditionRef.setValue("1 Klay 전송 중");
            } else
                Toast.makeText(this, "검증 요청 단계가 아닙니다.", Toast.LENGTH_SHORT).show();
        });

        btn_retry.setOnClickListener(view -> {
            if (text.contains("Key 인증 실패")) {
                    textView.setText("Holder에게 Key 인증 재요청 완료");
                    conditionRef.setValue("Key 인증 재요청");
            } else
                Toast.makeText(this, "Key 인증 확인 단계가 아닙니다.", Toast.LENGTH_SHORT).show();
        });

        btn_challenge.setOnClickListener(view -> {
            if (text.contains("Holder Key 인증 완료")) {
                challenge="";
                for(int i=0; i<32; i++) {
                    int num = (int) (Math.random()*10);
                    challenge = challenge.concat(String.valueOf(num));
                }
                challenge = aesEncode(challenge);
                textView.setText("Holder가 Response 생성 중..");
                conditionRef.setValue("challenge:\n" + challenge);
            } else
                Toast.makeText(this, "Challenge 생성 단계가 아닙니다.", Toast.LENGTH_SHORT).show();
        });

        btn_check.setOnClickListener(view -> {
            if (text.contains("Response:")) {
                response = text.split("\\n");
                if (response[1].contains(challenge)) {
                    Toast.makeText(Issuer.this, "사용자 검증 완료", Toast.LENGTH_SHORT).show();
                    textView.setText("사용자 검증이 완료 되었습니다.");
                    conditionRef.setValue("사용자 검증 완료");
                } else {
                    textView.setText("Response가 일치하지 않습니다. Holder에게 다시 Challenge를 보내세요.");
                    conditionRef.setValue("challenge:\n" + challenge);
                }
            } else
                Toast.makeText(this, "검증 완료 단계가 아닙니다.", Toast.LENGTH_SHORT).show();
        });

        button1.setOnClickListener(view -> {
            Intent intent1 = new Intent(getApplicationContext(), Choice_Roll.class);
            startActivity(intent1);
        });
    }
}