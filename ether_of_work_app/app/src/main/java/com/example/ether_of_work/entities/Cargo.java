package com.example.ether_of_work.entities;

public class Cargo {
    private String nome;

    public Cargo() {
        this("");
    }

    public Cargo(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
