package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Choice_Roll extends AppCompatActivity {

    public String text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_roll);

        EditText editText = findViewById(R.id.text);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

        editText.setOnLongClickListener(view -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            if (clipboard != null && clipboard.hasPrimaryClip()) {
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                String pasteText = item.getText().toString();
                editText.setText(pasteText);
                return true; // 클립보드 붙여넣기 동작을 처리했으므로 true 반환
            }
            return false; // 클립보드가 비어있거나 오류가 있을 경우 false 반환
        });

        button1.setOnClickListener(view -> {
            if (!editText.getText().toString().isEmpty()) {
                Intent intent = new Intent(getApplicationContext(), Holder.class);
                intent.putExtra("PublicKey", editText.getText().toString());
                startActivity(intent);
            } else
                Toast.makeText(this, "Kaikas 키를 입력하세요.", Toast.LENGTH_SHORT).show();
        });

        button2.setOnClickListener(view -> {
            if (!editText.getText().toString().isEmpty()) {
                Intent intent = new Intent(getApplicationContext(), Issuer.class);
                intent.putExtra("PublicKey", editText.getText().toString());
                startActivity(intent);
            } else
                Toast.makeText(this, "Kaikas 키를 입력하세요.", Toast.LENGTH_SHORT).show();
        });

        button3.setOnClickListener(view -> {
            if (!editText.getText().toString().isEmpty()) {
                Intent intent = new Intent(getApplicationContext(), Verifier.class);
                intent.putExtra("PublicKey", editText.getText().toString());
                startActivity(intent);
            } else
                Toast.makeText(this, "Kaikas 키를 입력하세요.", Toast.LENGTH_SHORT).show();
        });
    }
}