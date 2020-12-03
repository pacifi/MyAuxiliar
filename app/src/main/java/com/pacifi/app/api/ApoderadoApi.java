package com.pacifi.app.api;

import com.pacifi.app.models.Apoderado;
import com.pacifi.app.models.Curso;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import retrofit2.http.Path;

public interface ApoderadoApi {

    @GET("configuracion/apoderados/")
    Call<List<Apoderado>> list();

    @GET("configuracion/apoderados/{id}")
    Call<Curso> get(@Path("id") String apoderadoId);

    @POST("configuracion/apoderados/")
    Call<Void> add(@Body Apoderado apoderado);

    @PUT("configuracion/apoderados/{id}/")
    Call<Void> update(@Path("id") String apoderadoId, @Body Apoderado apoderado);

    @DELETE("configuracion/apoderados/{id}/")
    Call<Void> delete(@Path("id") String apoderadoId);

}
