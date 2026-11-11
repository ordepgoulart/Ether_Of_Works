package com.example.ether_of_work_backend.entities;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cargos")
public class Cargo {
    private ObjectId _id;
    private String nome;

    public Cargo(ObjectId _id, String nome) {
        this._id = _id;
        this.nome = nome;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
