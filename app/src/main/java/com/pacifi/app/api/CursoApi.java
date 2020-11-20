package com.pacifi.app.api;

import com.pacifi.app.models.Curso;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CursoApi {

    @GET("configuracion/cursos/")
    Call<List<Curso>> list();

    @GET("configuracion/cursos/{id}")
    Call<Curso> get(@Path("id") String groupId);

    @POST("configuracion/cursos/")
    Call<Void> add(@Body Curso curso);

    @PUT("configuracion/cursos/{id}/")
    Call<Void> update(@Path("id") String UsuarioId, @Body Curso curso);

    @DELETE("configuracion/cursos/{id}/")
    Call<Void> delete(@Path("id") String usuarioId);
}
