package com.example.ether_of_work_backend.DTO;

public class Erro {
    private String mensage;
    private boolean sucess;

    public Erro(String mensage, boolean sucess) {
        this.mensage = mensage;
        this.sucess = sucess;
    }

    public String getMensage() {
        return mensage;
    }

    public void setMensage(String mensage) {
        this.mensage = mensage;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }
}
