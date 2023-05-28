package com.example.toaqui.api;

import com.example.toaqui.model.Token;
import com.example.toaqui.model.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioService {

    @POST("api/auth")
    Call<Token> autenticar(@Body Usuario usuario);
}
