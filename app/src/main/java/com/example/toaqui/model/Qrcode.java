package com.example.toaqui.model;

public class Qrcode {

    private String codigo;
    private Object chamadId;

    public Qrcode() {
    }

    public Qrcode(String codigo, Long chamadId) {
        this.codigo = codigo;
        this.chamadId = chamadId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Object getChamadId() {
        return chamadId;
    }

    public void setChamadId(Object chamadId) {
        this.chamadId = chamadId;
    }
}
