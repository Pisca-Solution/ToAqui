package com.example.toaqui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toaqui.api.AlunoService;
import com.example.toaqui.api.ApiClient;
import com.example.toaqui.api.QrcodeService;
import com.example.toaqui.api.UsuarioService;
import com.example.toaqui.model.Aluno;
import com.example.toaqui.model.Qrcode;
import com.example.toaqui.model.Token;
import com.example.toaqui.model.Usuario;
import com.google.gson.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    TextView inputEmail;
    TextView inputSenha;

    String tempoExpiration;
    String authToken;

    Object chamadaId;

    Long alunoId;

    QrcodeService qrcodeService;

    UsuarioService usuarioService;

    AlunoService alunoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioService = ApiClient.getUsuarioService();

        btnLogin = findViewById(R.id.login);
        inputEmail = findViewById(R.id.inputLogin);
        inputSenha = findViewById(R.id.inputSenha);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = new Usuario();
                usuario.setEmail(inputEmail.getText().toString());
                usuario.setSenha(inputSenha.getText().toString());

                autenticar(usuario);
            }
        });
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

                try {
                    JSONObject obj = new JSONObject(result.getContents());

                    qrcode.setCodigo(obj.get("expiracao").toString());
                    qrcode.setChamadId(obj.get("chamadaId"));
                    chamadaId = obj.get("chamadaId");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                this.descriptografarMensagem(qrcode);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void autenticar(Usuario usuario){
        Call<Token> call = usuarioService.autenticar(usuario);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()){
                    if(Objects.equals(response.body().getPerfil(), "Aluno")){
                        qrcodeService = ApiClient.getQrcodeService(response.body().getToken());
                        alunoService = ApiClient.getAlunoService(response.body().getToken());

                        System.out.println(response.body().getToken());
                        authToken = response.body().getToken();

                        buscarDados(response.body().getClass_id());
                        scanQRCode();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Autenticação");
                        builder.setMessage("Não foi possível fazer login, tente acessar a plataforma de professor.");

                        AlertDialog alerta = builder.create();
                        alerta.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.e("Erro API", t.toString());
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

    private void descriptografarMensagem(Qrcode codigo){
        Call<String> call = qrcodeService.descriptografar(codigo);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                tempoExpiration = response.body();

                if(response.isSuccessful()){
                    if (Long.parseLong(tempoExpiration) < System.currentTimeMillis()) {
                        //O QRCode expirou, exibe uma mensagem para o usuário
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Scan QRCode");
                        builder.setMessage("Não foi possível confirmar presença, o QRCode expirou!");

                        AlertDialog alerta = builder.create();
                        alerta.show();
                    } else {
                        incluirAlunoChamada(alunoId);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Erro API", t.toString());
            }
        });
    }

    private void buscarDados(Long id){
        Call<Aluno> call = alunoService.buscarDados(id);

        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                if(response.isSuccessful()){
                    alunoId = response.body().getId();
                }
            }

            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                Log.e("Erro API", t.toString());
            }
        });
    }

    private void incluirAlunoChamada(Long alunoId){
        Call<String> call = alunoService.incluirAlunoChamada(chamadaId, alunoId);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println(response.body());
                if(response.isSuccessful()){
                    Bundle bundle = new Bundle();
                    bundle.putString("message", response.body());

                    Intent intent = new Intent(MainActivity.this, feedback.class);

                    intent.putExtras(bundle);
                    MainActivity.this.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Erro API", t.toString());
            }
        });
    }
}