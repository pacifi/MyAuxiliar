package com.pacifi.app.models;

public class Actividad {
    private String id;
    private String nombre;
    private String fecha;
    private String curso;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nonbre) {
        this.nombre = nonbre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "N°='" + nombre + '\'' +
                '}';
    }
}
