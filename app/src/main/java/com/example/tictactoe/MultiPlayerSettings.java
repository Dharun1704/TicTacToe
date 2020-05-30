package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MultiPlayerSettings extends AppCompatActivity {

    EditText playerX, playerO;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_setting_layout);

        playerX = findViewById(R.id.editText);
        playerO = findViewById(R.id.editText2);
        btn = findViewById(R.id.btnStart);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String XName = playerX.getText().toString();
                String OName = playerO.getText().toString();

                Intent i = new Intent(MultiPlayerSettings.this, Game.class);
                i.putExtra("XName",XName);
                i.putExtra("OName",OName);
                startActivity(i);
            }
        });
    }
}
