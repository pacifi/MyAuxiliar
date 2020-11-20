package com.pacifi.app.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterRetrofit {
    Retrofit retrofit;

    public AdapterRetrofit() {
    }

    public Retrofit getAdapter() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.50:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
