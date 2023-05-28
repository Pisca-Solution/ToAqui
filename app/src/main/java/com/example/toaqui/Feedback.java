package com.example.toaqui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Feedback extends AppCompatActivity {
    String mensagem = "";

    Button buttonSair;
    TextView msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        getSupportActionBar().hide();

        msg = findViewById(R.id.mensagemFeed);
        buttonSair = (Button) findViewById(R.id.btnSair);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mensagem = extras.getString("message");
        }

        msg.setText(mensagem);

        buttonSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}