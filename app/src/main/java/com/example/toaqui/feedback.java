package com.example.toaqui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class feedback extends AppCompatActivity {
    String mensagem = "";
    TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mensagem = extras.getString("message");
        }

        msg = findViewById(R.id.mensagemFeed);
        msg.setText(mensagem);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }
}