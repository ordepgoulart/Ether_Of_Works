package com.example.ether_of_work.entities;

import com.example.ether_of_work.dto.Erro;

public class Interessado {
    private Vaga vaga;
    private Candidato candidato;

    public Interessado() {
        this(new Vaga(), new Candidato());
    }

    public Interessado(Vaga vaga, Candidato candidato) {
        this.vaga = vaga;
        this.candidato = candidato;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    // verifica se um campo de texto não foi preenchido
    private boolean vazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    // valida a vaga e os dados do candidato antes de enviar para a web api
    public Erro validar() {
        String erro = "";

        if (vaga == null || vazio(vaga.getRegistro()))
            erro = "A vaga é obrigatória";
        else if (candidato == null)
            erro = "O candidato é obrigatório";
        else {
            Erro erroCandidato = candidato.validar();
            if (!erroCandidato.isSucesso())
                erro = erroCandidato.getMensagem();
        }

        return new Erro(erro, erro.isEmpty());
    }
}
