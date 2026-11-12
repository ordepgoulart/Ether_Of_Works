package com.example.ether_of_work.utils;

import com.example.ether_of_work.entities.Cargo;
import com.example.ether_of_work.entities.Empresa;
import com.example.ether_of_work.entities.Vaga;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public final class JsonUtil {
    private static final Gson GSON = new Gson();

    private JsonUtil() {
    }

    public static String corpo(ResponseBody body) {
        String texto = "";
        if (body != null) {
            try {
                texto = body.string();
            } catch (IOException e) {
                texto = "";
            }
        }
        return texto;
    }

    private static boolean iniciaLista(String json) {
        return json != null && json.trim().startsWith("[");
    }

    public static List<Vaga> vagas(String json) {
        List<Vaga> lista = new ArrayList<>();
        if (iniciaLista(json)) {
            Type tipo = new TypeToken<List<Vaga>>() {
            }.getType();
            List<Vaga> convertida = GSON.fromJson(json, tipo);
            if (convertida != null) {
                lista = convertida;
            }
        }
        return lista;
    }

    public static List<Empresa> empresas(String json) {
        List<Empresa> lista = new ArrayList<>();
        if (iniciaLista(json)) {
            Type tipo = new TypeToken<List<Empresa>>() {
            }.getType();
            List<Empresa> convertida = GSON.fromJson(json, tipo);
            if (convertida != null) {
                lista = convertida;
            }
        }
        return lista;
    }

    public static List<Cargo> cargos(String json) {
        List<Cargo> lista = new ArrayList<>();
        if (iniciaLista(json)) {
            Type tipo = new TypeToken<List<Cargo>>() {
            }.getType();
            List<Cargo> convertida = GSON.fromJson(json, tipo);
            if (convertida != null) {
                lista = convertida;
            }
        }
        return lista;
    }

    public static Vaga vaga(String json) {
        return GSON.fromJson(json, Vaga.class);
    }

    public static String json(Object objeto) {
        return GSON.toJson(objeto);
    }

    public static String mensagem(String corpo) {
        String resposta = corpo == null ? "" : corpo.trim();
        if (resposta.length() >= 2 && resposta.startsWith("\"") && resposta.endsWith("\"")) {
            resposta = resposta.substring(1, resposta.length() - 1);
        }
        return resposta;
    }
}
