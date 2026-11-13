package com.example.ether_of_work.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class Vaga {
    // o id (ObjectId) não é guardado no app de propósito:
    // o Spring envia o ObjectId como {"timestamp":..., "date":...} e não consegue
    // ler esse formato de volta quando a vaga é enviada junto com o interessado (erro 400).
    // A vaga é identificada pelo registro.
    private String registro;
    private String cargo;
    private String cidade;
    private String estado;
    @SerializedName("pre_requisitos")
    private String preRequisitos;
    private String formacao;
    @SerializedName("conhecimentos_requeridos")
    private String conhecimentosRequeridos;
    private String regime;
    @SerializedName("jornada_trabalho")
    private String jornadaTrabalho;
    private String remuneracao;
    private Empresa empresa;

    public Vaga() {
        this("", "", "", "", "", "", "", "", "", "", new Empresa());
    }

    public Vaga(String registro, String cargo, String cidade, String estado, String preRequisitos,
                String formacao, String conhecimentosRequeridos, String regime, String jornadaTrabalho,
                String remuneracao, Empresa empresa) {
        this.registro = registro;
        this.cargo = cargo;
        this.cidade = cidade;
        this.estado = estado;
        this.preRequisitos = preRequisitos;
        this.formacao = formacao;
        this.conhecimentosRequeridos = conhecimentosRequeridos;
        this.regime = regime;
        this.jornadaTrabalho = jornadaTrabalho;
        this.remuneracao = remuneracao;
        this.empresa = empresa;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPreRequisitos() {
        return preRequisitos;
    }

    public void setPreRequisitos(String preRequisitos) {
        this.preRequisitos = preRequisitos;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public String getConhecimentosRequeridos() {
        return conhecimentosRequeridos;
    }

    public void setConhecimentosRequeridos(String conhecimentosRequeridos) {
        this.conhecimentosRequeridos = conhecimentosRequeridos;
    }

    public String getRegime() {
        return regime;
    }

    public void setRegime(String regime) {
        this.regime = regime;
    }

    public String getJornadaTrabalho() {
        return jornadaTrabalho;
    }

    public void setJornadaTrabalho(String jornadaTrabalho) {
        this.jornadaTrabalho = jornadaTrabalho;
    }

    public String getRemuneracao() {
        return remuneracao;
    }

    public void setRemuneracao(String remuneracao) {
        this.remuneracao = remuneracao;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    // devolve o texto sem espaços nas pontas ("" quando for null)
    private static String texto(String valor) {
        return valor == null ? "" : valor.trim();
    }

    public String nomeEmpresa() {
        return empresa == null ? "" : texto(empresa.getNomeFantasia());
    }

    public String tipoEmpresa() {
        return empresa == null ? "" : texto(empresa.getTipo());
    }

    // cidade - UF
    public String montarLocal() {
        String cidadeVaga = texto(cidade);
        String estadoVaga = texto(estado).toUpperCase(Locale.ROOT);
        String local = cidadeVaga + estadoVaga;
        if (!cidadeVaga.isEmpty() && !estadoVaga.isEmpty()) {
            local = cidadeVaga + " - " + estadoVaga;
        }
        return local;
    }

    // usado na pesquisa da tela principal (cargo, empresa, cidade ou estado)
    public boolean contem(String termo) {
        String busca = texto(termo).toLowerCase(Locale.ROOT);
        String conteudo = (texto(cargo) + " " + nomeEmpresa() + " " + texto(cidade) + " " + texto(estado))
                .toLowerCase(Locale.ROOT);
        return busca.isEmpty() || conteudo.contains(busca);
    }
}
