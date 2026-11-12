package com.example.ether_of_work.RetrofitConfigs;

import com.example.ether_of_work.Services.EtherService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    // Emulador Android: 10.0.2.2 aponta para o localhost do PC. Em celular fisico use o IP do PC na rede.
    // Precisa começar com http:// e terminar com "/".
    public static final String BASE_URL = "http://10.0.2.2:8080/";

    private static Retrofit retrofit;
    private static RetrofitConfig instancia;

    private RetrofitConfig() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static RetrofitConfig getInstance() {
        if (instancia == null) {
            instancia = new RetrofitConfig();
        }
        return instancia;
    }

    public EtherService getEtherService() {
        return retrofit.create(EtherService.class);
    }
}
