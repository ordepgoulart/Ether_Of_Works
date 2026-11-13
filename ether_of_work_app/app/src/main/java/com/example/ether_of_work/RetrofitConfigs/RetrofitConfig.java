package com.example.ether_of_work.RetrofitConfigs;

import com.example.ether_of_work.Services.IEtherService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    public static final String BASE_URL = "http://192.168.0.169:8080/ether/";

    private static Retrofit retrofit;

    public RetrofitConfig() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
    }

    public static RetrofitConfig getInstance() {
        return new RetrofitConfig();
    }

    public IEtherService getEtherService() {
        return retrofit.create(IEtherService.class);
    }
}
