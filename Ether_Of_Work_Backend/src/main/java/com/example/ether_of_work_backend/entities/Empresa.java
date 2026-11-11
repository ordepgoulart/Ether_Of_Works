package com.example.ether_of_work_backend.entities;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "empresas")
public class Empresa {

    private ObjectId _id;
    @Field("nome_fantasia")
    private String nome_fantasia;
    @Field("razao_social")
    private String razao_social;
    @Field("tipo")
    private String tipo;

    public Empresa(ObjectId _id, String nome_fantasia, String razao_social, String tipo) {
        this._id = _id;
        this.nome_fantasia = nome_fantasia;
        this.razao_social = razao_social;
        this.tipo = tipo;
    }

    public Empresa() {
        this(new ObjectId(),"","","");
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
