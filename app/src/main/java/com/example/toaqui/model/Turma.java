package com.example.toaqui.model;

public class Turma {
    private Long id;
    private String codigoTurma;
    private String curso;
    private String periodoLetivo;
    private String diaSemana;
    private String codigoDisciplina;
    private String professor;
    private Long idProfessor;
    private String nomeDisciplina;
    private HoraAula[] horasAula;

    public Turma() {
    }

    public Long getId() {
        return id;
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }

    public String getCurso() {
        return curso;
    }

    public String getPeriodoLetivo() {
        return periodoLetivo;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public String getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public String getProfessor() {
        return professor;
    }

    public Long getIdProfessor() {
        return idProfessor;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public HoraAula[] getHorasAula() {
        return horasAula;
    }
}
