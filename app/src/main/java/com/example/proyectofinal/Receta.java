package com.example.proyectofinal;

public class Receta {

    private String usuario, tituloReceta, ingredientes, pasosPreparacion, imagen;
    private int tiempoPreparacion;
    private boolean vegetariano, vegano, sinGluten;

    public Receta(String usuario, String tituloReceta, int tiempoPreparacion, String ingredientes,
                  String pasosPreparacion, boolean vegetariano, boolean vegano, boolean sinGluten, String imagen) {
        this.usuario = usuario;
        this.tituloReceta = tituloReceta;
        this.tiempoPreparacion = tiempoPreparacion;
        this.ingredientes = ingredientes;
        this.pasosPreparacion = pasosPreparacion;
        this.vegano = vegano;
        this.vegetariano = vegetariano;
        this.sinGluten = sinGluten;
        this.imagen = imagen;
    }

}
