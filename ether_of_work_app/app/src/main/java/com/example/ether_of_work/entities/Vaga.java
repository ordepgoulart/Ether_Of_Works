package com.example.ether_of_work.entities;

import com.example.ether_of_work.dto.Erro;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

public class Vaga {
    private static final String REGEX_ESTADO = "^[A-Za-z]{2}$";
    private static final String REGEX_REMUNERACAO = "^(R\\$\\s?)?\\d{1,3}(\\.?\\d{3})*(,\\d{2})?$";

    private JsonElement id;
    @SerializedName("_id")
    private JsonElement idMongo;
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

    public JsonElement getId() {
        return id;
    }

    public void setId(JsonElement id) {
        this.id = id;
    }

    public JsonElement getIdMongo() {
        return idMongo;
    }

    public void setIdMongo(JsonElement idMongo) {
        this.idMongo = idMongo;
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

    private boolean vazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    public Erro validar() {
        String erro = "";

        if (vazio(registro))
            erro = "O registro é obrigatório";
        else if (vazio(cargo))
            erro = "O cargo é obrigatório";
        else if (empresa == null || vazio(empresa.getNomeFantasia()))
            erro = "A empresa é obrigatória";
        else if (vazio(cidade))
            erro = "A cidade é obrigatória";
        else if (vazio(estado))
            erro = "O estado é obrigatório";
        else if (!estado.trim().matches(REGEX_ESTADO))
            erro = "O formato do estado é inválido (UF com 2 letras, ex: SP)";
        else if (vazio(preRequisitos))
            erro = "Os pré-requisitos são obrigatórios";
        else if (vazio(formacao))
            erro = "A formação é obrigatória";
        else if (vazio(conhecimentosRequeridos))
            erro = "Os conhecimentos requeridos são obrigatórios";
        else if (vazio(regime))
            erro = "O regime é obrigatório";
        else if (!regime.trim().equalsIgnoreCase("CLT") && !regime.trim().equalsIgnoreCase("PJ"))
            erro = "O regime deve ser CLT ou PJ";
        else if (vazio(jornadaTrabalho))
            erro = "A jornada de trabalho é obrigatória";
        else if (vazio(remuneracao))
            erro = "A remuneração é obrigatória";
        else if (!remuneracao.trim().matches(REGEX_REMUNERACAO))
            erro = "O formato da remuneração é inválido (ex: R$ 5.000,00)";

        return new Erro(erro, erro.isBlank());
    }
}
