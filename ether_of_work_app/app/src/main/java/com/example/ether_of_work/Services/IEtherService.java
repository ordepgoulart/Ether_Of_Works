package com.example.ether_of_work.Services;

import com.example.ether_of_work.entities.Interessado;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IEtherService {

    // a web api devolve a lista de vagas ou um texto ("Não há vagas cadastradas.")
    // por isso a resposta é lida como ResponseBody e convertida no JsonUtil
    @GET("get-all-vagas")
    Call<ResponseBody> getAllVagas();

    // registra o interesse do candidato pela vaga
    // precisa existir no backend: @PostMapping("add-interessado")
    @POST("add-interessado")
    Call<ResponseBody> addInteressado(@Body Interessado interessado);
}
