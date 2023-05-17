package com.example.toaqui.api;

import com.example.toaqui.model.Aluno;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AlunoService {

    @GET("alunos/{id}")
    Call<Aluno> buscarDados(@Path("id") Long id);

    @POST("chamadas/{chamadaId}/alunos/{alunoId}")
    Call<String> incluirAlunoChamada(@Path("chamadaId") Object chamadaId, @Path("alunoId") Long alunoId);
}
