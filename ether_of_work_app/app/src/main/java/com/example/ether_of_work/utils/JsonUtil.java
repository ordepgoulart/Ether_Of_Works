package com.example.ether_of_work.utils;

import com.example.ether_of_work.entities.Vaga;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public final class JsonUtil {
    private static final Gson GSON = new Gson();

    private JsonUtil() {
    }

    // lê o texto da resposta
    // quando a requisição dá erro (400, 404, 500...) o conteúdo vem no errorBody e não no body
    public static String corpo(Response<ResponseBody> response) {
        ResponseBody body = response.isSuccessful() ? response.body() : response.errorBody();
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

    // converte o json em lista de vagas
    // quando a web api devolve um texto (sem vagas) a lista fica vazia
    public static List<Vaga> vagas(String json) {
        List<Vaga> lista = new ArrayList<>();
        if (json != null && json.trim().startsWith("[")) {
            Type tipo = new TypeToken<List<Vaga>>() {
            }.getType();
            List<Vaga> convertida = GSON.fromJson(json, tipo);
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

    // extrai a mensagem da resposta da web api
    // {"erro": "..."} --> padrão do validar() do backend
    // {"message": "..."} --> erro padrão do Spring
    // "texto" ou texto --> mensagens simples do controller
    public static String mensagem(String corpo) {
        String resposta = corpo == null ? "" : corpo.trim();
        if (resposta.startsWith("{")) {
            resposta = campoMensagem(resposta);
        } else if (resposta.length() >= 2 && resposta.startsWith("\"") && resposta.endsWith("\"")) {
            resposta = resposta.substring(1, resposta.length() - 1);
        }
        return resposta;
    }

    private static String campoMensagem(String json) {
        String mensagem = "";
        try {
            JsonObject objeto = JsonParser.parseString(json).getAsJsonObject();
            if (objeto.has("erro") && !objeto.get("erro").isJsonNull()) {
                mensagem = objeto.get("erro").getAsString();
            } else if (objeto.has("message") && !objeto.get("message").isJsonNull()) {
                mensagem = objeto.get("message").getAsString();
            }
        } catch (RuntimeException e) {
            mensagem = "";
        }
        return mensagem;
    }
}
