package com.example.toaqui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.toaqui.api.ApiClient;
import com.example.toaqui.api.QrcodeService;
import com.example.toaqui.model.Qrcode;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;

    String tempoExpiration;

    QrcodeService qrcodeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrcodeService = ApiClient.getQrcodeService();
        btnLogin = findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQRCode();
            }
        });

    }

    public void scanQRCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan QR Code");
        integrator.setOrientationLocked(true);
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Qrcode qrcode = new Qrcode();

                qrcode.setCodigo(result.getContents());

                this.descriptografarMensagem(qrcode);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void descriptografarMensagem(Qrcode codigo){
        Call<String> call = qrcodeService.descriptografar(codigo);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                tempoExpiration = response.body();

                if(response.isSuccessful()){
                    if (Long.parseLong(tempoExpiration) < System.currentTimeMillis()) {
                         //O QRCode expirou, exibe uma mensagem para o usuário
                        Toast.makeText(MainActivity.this, "O QRCode expirou!", Toast.LENGTH_SHORT).show();
                    } else {
                         //O QRCode ainda é válido, envia os dados para o back-end
                        Toast.makeText(MainActivity.this, "O QRCode Válido !", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Erro API", t.toString());
            }
        });
    }
}