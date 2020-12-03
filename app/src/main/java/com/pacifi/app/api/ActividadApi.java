package com.pacifi.app.api;

import com.pacifi.app.models.Actividad;
import com.pacifi.app.models.Curso;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ActividadApi {

    @GET("configuracion/actividades/")
    Call<List<Actividad>> list();

    @GET("configuracion/actividades/")
    Call<List<Actividad>> list(@Query("curso") String curso);
}
