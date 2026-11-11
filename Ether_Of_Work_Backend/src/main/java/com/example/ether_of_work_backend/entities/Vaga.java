package com.example.ether_of_work_backend.entities;

import com.example.ether_of_work_backend.DTO.Erro;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "vagas")
public class Vaga {
    private static final String REGEX_ESTADO = "^[A-Za-z]{2}$";
    private static final String REGEX_REMUNERACAO = "^(R\\$\\s?)?\\d{1,3}(\\.?\\d{3})*(,\\d{2})?$";

    private ObjectId _id;
    @Field("registro")
    private String registro;
    @Field("cargo")
    private String cargo;
    @Field("cidade")
    private String cidade;
    @Field("estado")
    private String estado;
    @Field("pre_requisitos")
    private String pre_requisitos;
    @Field("formacao")
    private String formacao;
    @Field("conhecimentos_requeridos")
    private String conhecimentos_requeridos;
    @Field("regime")
    private String regime;
    @Field("jornada_trabalho")
    private String jornada_trabalho;
    @Field("remuneracao")
    private String remuneracao;
    @Field("empresa")
    private Empresa empresa;

    public Vaga(ObjectId _id, String registro, String cargo, String cidade, String estado, String pre_requisitos, String formacao, String conhecimentos_requeridos, String regime, String jornada_trabalho, String remuneracao, Empresa empresa) {
        this._id = _id;
        this.registro = registro;
        this.cargo = cargo;
        this.cidade = cidade;
        this.estado = estado;
        this.pre_requisitos = pre_requisitos;
        this.formacao = formacao;
        this.conhecimentos_requeridos = conhecimentos_requeridos;
        this.regime = regime;
        this.jornada_trabalho = jornada_trabalho;
        this.remuneracao = remuneracao;
        this.empresa = empresa;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
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

    public String getPre_requisitos() {
        return pre_requisitos;
    }

    public void setPre_requisitos(String pre_requisitos) {
        this.pre_requisitos = pre_requisitos;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public String getConhecimentos_requeridos() {
        return conhecimentos_requeridos;
    }

    public void setConhecimentos_requeridos(String conhecimentos_requeridos) {
        this.conhecimentos_requeridos = conhecimentos_requeridos;
    }

    public String getRegime() {
        return regime;
    }

    public void setRegime(String regime) {
        this.regime = regime;
    }

    public String getJornada_trabalho() {
        return jornada_trabalho;
    }

    public void setJornada_trabalho(String jornada_trabalho) {
        this.jornada_trabalho = jornada_trabalho;
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

    public Vaga() {
this(new ObjectId(),"","","","","","","","","","",new Empresa());
    }

    public Erro validar() {
        String erro = "";


        if(vazio(registro))
            erro = "O registro é obrigatório";
        else if(vazio(cargo))
            erro = "O cargo é obrigatório";
        else if(empresa == null || vazio(empresa.getNome_fantasia()))
            erro = "A empresa é obrigatória";
        else if(vazio(cidade))
            erro = "A cidade é obrigatória";
        else if(vazio(estado))
            erro = "O estado é obrigatório";
        else if(!estado.trim().matches(REGEX_ESTADO))
            erro = "O formato do estado é inválido (UF com 2 letras, ex: SP)";
        else if(vazio(pre_requisitos))
            erro = "Os pré-requisitos são obrigatórios";
        else if(vazio(formacao))
            erro = "A formação é obrigatória";
        else if(vazio(conhecimentos_requeridos))
            erro = "Os conhecimentos requeridos são obrigatórios";
        else if(vazio(regime))
            erro = "O regime é obrigatório";
        else if(!regime.trim().equalsIgnoreCase("CLT") && !regime.trim().equalsIgnoreCase("PJ"))
            erro = "O regime deve ser CLT ou PJ";
        else if(vazio(jornada_trabalho))
            erro = "A jornada de trabalho é obrigatória";
        else if(vazio(remuneracao))
            erro = "A remuneração é obrigatória";
        else if(!remuneracao.trim().matches(REGEX_REMUNERACAO))
            erro = "O formato da remuneração é inválido (ex: R$ 5.000,00)";

        return new Erro(erro, erro.isBlank());
    }

}
