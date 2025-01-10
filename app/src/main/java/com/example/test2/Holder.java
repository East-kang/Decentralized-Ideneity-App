package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Holder extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    private Button btn_clear, btn_send, btn_response, btn_key, button1, button2;
    public String key, text;
    public static String secretKey, response;
    public static byte[] ivBytes;
    public String[] challenge;

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
        return str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder);

        textView = (TextView) findViewById(R.id.textview);
        editText = (EditText) findViewById(R.id.editText);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_key = (Button) findViewById(R.id.btn_key);
        btn_response = (Button) findViewById(R.id.btn_response);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        Intent intent = getIntent();
        key = intent.getExtras().getString("PublicKey");

        textView.setText("'검증 요청' 버튼을 클릭하여 Issuer에게 사용자 검증 요청을 보내세요.");

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
    }

    @Override
    protected void onStart() {
        super.onStart();

        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                text = dataSnapshot.getValue(String.class);

                if (text.contains("1 Klay 전송 중"))
                    textView.setText("Kaikas 앱의 klaytnfinder를 통해 Issuer의 Key를 아래에 입력한 뒤 'Key 인증' 버튼을 누르세요.");

                if (text.contains("Key 인증 재요청"))
                    textView.setText("Issuer의 Key를 아래에 맞게 다시 입력한 뒤 'Key 인증' 버튼을 누르세요.");

                if (text.contains("Holder Key 인증 완료"))
                    textView.setText("Issuer가 Response 요청을 보낼 때까지 대기 해주세요.");

                if (text.contains("challenge:")){
                    challenge = text.split("\\n");
                    textView.setText("'Response 생성' 버튼을 눌러 Issuer에게 Response를 전송하세요.");
                }

                if (text.contains("VP 제출")){
                    textView.setText("사용자 검증이 완료.\n'다음' 버튼을 눌러 VC를 생성해주세요.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_clear.setOnClickListener(view -> {
            textView.setText("'검증 요청' 버튼을 클릭하여 Issuer에게 사용자 검증 요청을 보내세요.");
            editText.setText("");
            conditionRef.setValue("");
            Toast.makeText(this, "초기화 완료", Toast.LENGTH_SHORT).show();
        });

        btn_send.setOnClickListener(view -> {
            if (textView.getText().toString().contains("'검증 요청' 버튼을 클릭하여 Issuer에게 사용자 검증 요청을 보내세요.")) {
                textView.setText("Issuer에게 사용자 검증 요청 중..\nIssuer로부터 Key 요청이 오면 'Key 인증' 버튼을 눌러 인증하세요.");
                conditionRef.setValue("▼ Holder 공개 키 ▼\n" + key);
            } else {
                Toast.makeText(this, "검증 요청 단계가 아닙니다.", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "초기화를 원하면 'clear' 버튼을 눌러주세요.", Toast.LENGTH_SHORT).show();
            }
        });

        btn_key.setOnClickListener(view -> {
            if ((text.contains("1 Klay 전송 중")) || (text.contains("Key 인증 재요청")) ){
                textView.setText("Issuer Key 보내기 성공\nIssuer에게 Challenge 요청 대기 중..");
                conditionRef.setValue("▼ Issuer Key 확인 ▼\n" + editText.getText().toString());
            } else
                Toast.makeText(this, "Key 인증 단계가 아닙니다.", Toast.LENGTH_SHORT).show();
        });

        btn_response.setOnClickListener(view -> {
            if (text.contains("challenge:")) {
                response = challenge[1];
                textView.setText("Issuer가 Response 확인 중..");
                conditionRef.setValue("Response:\n" + response);
            } else
                Toast.makeText(this, "Response 생성 단계가 아닙니다.", Toast.LENGTH_SHORT).show();
        });

        button1.setOnClickListener(view -> {
            Intent intent1 = new Intent(getApplicationContext(), Choice_Roll.class);
            startActivity(intent1);
        });

        button2.setOnClickListener(view -> {
            if (text.contains("VP 제출")) {
                Intent intent1 = new Intent(getApplicationContext(), InitActivity.class);
                intent1.putExtra("PublicKey", key);
                startActivity(intent1);
            } else
                Toast.makeText(this, "사용자 검증이 완료되지 않았습니다.", Toast.LENGTH_SHORT).show();
        });
    }
}