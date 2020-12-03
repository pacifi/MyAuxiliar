package com.pacifi.app.api;

import com.pacifi.app.models.Curso;
import com.pacifi.app.models.Estudiante;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EstudianteApi {

    @GET("configuracion/estudiantes/")
    Call<List<Estudiante>> list();

    @GET("configuracion/estudiantes/{id}")
    Call<Estudiante> get(@Path("id") String estudianteId);

    @POST("configuracion/estudiantes/")
    Call<Void> add(@Body Estudiante estudiante);

    @PUT("configuracion/estudiantes/{id}/")
    Call<Void> update(@Path("id") String estudianteId, @Body Estudiante estudiante);

    @DELETE("configuracion/estudiantes/{id}/")
    Call<Void> delete(@Path("id") String estudianteId);
}
