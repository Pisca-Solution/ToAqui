package com.example.toaqui.model;

public class Aluno {
    private Long id;
    private String nome;
    private int ra;
    private String email;
    private String senha;
    private Turma[] turmas;

    public Aluno() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getRa() {
        return ra;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public Turma[] getTurmas() {
        return turmas;
    }
}
