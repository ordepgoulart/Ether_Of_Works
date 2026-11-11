package com.example.ether_of_work_backend.entities;

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
}
