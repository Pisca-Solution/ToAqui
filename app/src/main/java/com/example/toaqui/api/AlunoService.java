package com.example.toaqui.api;

import com.example.toaqui.model.Mensagem;

import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AlunoService {

    @PUT("chamadas/{chamadaId}/alunos/{alunoId}")
    Call<Mensagem> incluirAlunoChamada(@Path("chamadaId") Long chamadaId, @Path("alunoId") Long alunoId);
}
