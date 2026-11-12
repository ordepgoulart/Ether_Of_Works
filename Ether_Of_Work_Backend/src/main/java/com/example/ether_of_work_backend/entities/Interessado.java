package com.example.ether_of_work_backend.entities;

import com.example.ether_of_work_backend.DTO.Erro;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("interessados")
public class Interessado {
    private ObjectId _id;
    @Field("vaga")
    private Vaga vaga;
    @Field("candidato")
    private Candidato  candidato;

    private static final String REGEX_CPF = "^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$";
    private static final String REGEX_TELEFONE = "^\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$";
    private static final String REGEX_EMAIL = "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$";


    public Interessado(ObjectId _id, Vaga vaga, Candidato candidato) {
        this._id = _id;
        this.vaga = vaga;
        this.candidato = candidato;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
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

    private boolean vazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
    public Erro validar() {
        String erro = "";

        if(vaga == null || vazio(vaga.getRegistro()))
            erro = "A vaga é obrigatória";
        else if(candidato == null)
            erro = "O candidato é obrigatório";
        else if(vazio(candidato.getNome()))
            erro = "O nome do candidato é obrigatório";
        else if(vazio(candidato.getCpf()))
            erro = "O CPF do candidato é obrigatório";
        else if(!candidato.getCpf().trim().matches(REGEX_CPF))
            erro = "O formato do CPF é inválido (000.000.000-00)";
        else if(vazio(candidato.getEmail()))
            erro = "O e-mail do candidato é obrigatório";
        else if(!candidato.getEmail().trim().matches(REGEX_EMAIL))
            erro = "O formato do e-mail é inválido (nome@dominio.com)";
        else if(vazio(candidato.getTelefone()))
            erro = "O telefone do candidato é obrigatório";
        else if(!candidato.getTelefone().trim().matches(REGEX_TELEFONE))
            erro = "O formato de telefone é inválido ((00) 00000-0000)";
        else if(vazio(candidato.getFormacao()))
            erro = "A formação do candidato é obrigatória";

        return new Erro(erro, erro.isBlank());
    }

}
