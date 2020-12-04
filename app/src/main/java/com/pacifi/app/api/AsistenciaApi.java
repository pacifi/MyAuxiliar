package com.pacifi.app.api;

import com.pacifi.app.models.AsistenciaEstudianteApoderado;
import com.pacifi.app.models.Curso;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AsistenciaApi {


    @GET("configuracion/asistencias/")
    Call<List<AsistenciaEstudianteApoderado>> listasistencia(@Query("actividad") String actividadId);


}

