package com.example.ether_of_work.entities;

import com.example.ether_of_work.dto.Erro;

public class Candidato {
    private static final String REGEX_CPF = "^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$";
    private static final String REGEX_TELEFONE = "^\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$";
    private static final String REGEX_EMAIL = "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$";

    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String formacao;

    public Candidato() {
        this("", "", "", "", "");
    }

    public Candidato(String nome, String cpf, String email, String telefone, String formacao) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.formacao = formacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    // verifica se um campo de texto não foi preenchido
    private boolean vazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    // mesmas regras do validar() da classe Interessado no backend
    // devolve sucesso = false com a mensagem do primeiro erro encontrado
    public Erro validar() {
        String erro = "";

        if (vazio(nome))
            erro = "O nome do candidato é obrigatório";
        else if (vazio(cpf))
            erro = "O CPF do candidato é obrigatório";
        else if (!cpf.trim().matches(REGEX_CPF))
            erro = "O formato do CPF é inválido (000.000.000-00)";
        else if (vazio(email))
            erro = "O e-mail do candidato é obrigatório";
        else if (!email.trim().matches(REGEX_EMAIL))
            erro = "O formato do e-mail é inválido (nome@dominio.com)";
        else if (vazio(telefone))
            erro = "O telefone do candidato é obrigatório";
        else if (!telefone.trim().matches(REGEX_TELEFONE))
            erro = "O formato de telefone é inválido ((00) 00000-0000)";
        else if (vazio(formacao))
            erro = "A formação do candidato é obrigatória";

        return new Erro(erro, erro.isEmpty());
    }
}
