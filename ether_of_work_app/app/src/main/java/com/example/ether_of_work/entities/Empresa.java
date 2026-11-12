package com.example.ether_of_work.entities;

import com.google.gson.annotations.SerializedName;

public class Empresa {
    @SerializedName("nome_fantasia")
    private String nomeFantasia;
    @SerializedName("razao_social")
    private String razaoSocial;
    private String tipo;

    public Empresa() {
        this("", "", "");
    }

    public Empresa(String nomeFantasia, String razaoSocial, String tipo) {
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.tipo = tipo;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
