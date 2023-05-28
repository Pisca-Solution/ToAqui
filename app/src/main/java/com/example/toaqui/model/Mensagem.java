package com.example.toaqui.model;

public class Mensagem {

    private String mensagem;
    private Boolean success;

    public Mensagem() {
    }

    public Mensagem(String mensagem, Boolean success) {
        this.mensagem = mensagem;
        this.success = success;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
