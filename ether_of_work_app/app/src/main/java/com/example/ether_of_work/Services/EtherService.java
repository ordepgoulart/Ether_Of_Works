package com.example.ether_of_work.Services;

import com.example.ether_of_work.entities.Interessado;
import com.example.ether_of_work.entities.Vaga;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EtherService {

    @GET("get-all-vagas")
    Call<ResponseBody> getAllVagas();

    @GET("get-all-empresas")
    Call<ResponseBody> getAllEmpresas();

    @GET("get-all-cargos")
    Call<ResponseBody> getAllCargos();

    @POST("add-vaga")
    Call<ResponseBody> addVaga(@Body Vaga vaga);

    @PUT("update-vaga")
    Call<ResponseBody> updateVaga(@Body Vaga vaga);

    @DELETE("delete-vaga/{registro}")
    Call<ResponseBody> deleteVaga(@Path("registro") String registro);

    @POST("add-interessado")
    Call<ResponseBody> addInteressado(@Body Interessado interessado);
}
