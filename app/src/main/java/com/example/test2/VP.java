package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VP extends AppCompatActivity {

    private String dataText1, dataText2, dataText3, dataText4, dataText5, dataText6, dataText7, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vp);

        final CheckBox cb1 = (CheckBox)findViewById(R.id.checkBox1);
        final CheckBox cb2 = (CheckBox)findViewById(R.id.checkBox2);
        final CheckBox cb3 = (CheckBox)findViewById(R.id.checkBox3);
        final CheckBox cb4 = (CheckBox)findViewById(R.id.checkBox4);
        final CheckBox cb5 = (CheckBox)findViewById(R.id.checkBox5);
        final CheckBox cb6 = (CheckBox)findViewById(R.id.checkBox6);
        final CheckBox cb7 = (CheckBox)findViewById(R.id.checkBox7);
        Button button = findViewById(R.id.button);

        Intent intent = getIntent();
        dataText1 = intent.getExtras().getString("DID1");
        dataText2 = intent.getExtras().getString("DID2");
        dataText3 = intent.getExtras().getString("DID3");
        dataText4 = intent.getExtras().getString("DID4");
        dataText5 = intent.getExtras().getString("DID5");
        dataText6 = intent.getExtras().getString("DID6");
        dataText7 = intent.getExtras().getString("DID7");
        key = intent.getExtras().getString("key");

        button.setOnClickListener(view -> {
            String result = "Public Key:" + key;
            if(cb1.isChecked() == true) result += "\nName:" + dataText1;
            if(cb2.isChecked() == true) result += "\nGender:" + dataText2;
            if(cb3.isChecked() == true) result += "\nDate Of Birth:" + dataText3;
            if(cb4.isChecked() == true) result += "\nCriminal Record:" + dataText4;
            if(cb5.isChecked() == true) result += "\nNationality:" + dataText5;
            if(cb6.isChecked() == true) result += "\nAcademic Degree:" + dataText6;
            if(cb7.isChecked() == true) result += "\nAvatar Hyperlink:" + dataText7;

            Intent intent1 = new Intent(getApplicationContext(), Sub_VP.class);
            intent1.putExtra("VP", result);
            intent1.putExtra("DID1", dataText1);
            intent1.putExtra("DID2", dataText2);
            intent1.putExtra("DID3", dataText3);
            intent1.putExtra("DID4", dataText4);
            intent1.putExtra("DID5", dataText5);
            intent1.putExtra("DID6", dataText6);
            intent1.putExtra("DID7", dataText7);
            intent1.putExtra("key", key);
            startActivity(intent1);
        });
    }
}