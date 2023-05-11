package com.example.proyectofinal;

public class Receta {

    private String usuario, nombre, ingredientes, instrucciones, urlFoto;
    private int idReceta, tiempo;
    private boolean vegetariano, vegano, sinGluten;

    public Receta(int idReceta, String usuario, String nombre, int tiempo, String ingredientes,
                  String instrucciones, boolean vegetariano, boolean vegano, boolean sinGluten, String urlFoto) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.ingredientes = ingredientes;
        this.instrucciones = instrucciones;
        this.vegano = vegano;
        this.vegetariano = vegetariano;
        this.sinGluten = sinGluten;
        this.urlFoto = urlFoto;
    }

    // Funcion para modificar cualquier campo de la receta
    public void modificarReceta(String usuario, String nombre, int tiempo, String ingredientes,
                           String instrucciones, boolean vegetariano, boolean vegano, boolean sinGluten, String urlFoto) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.ingredientes = ingredientes;
        this.instrucciones = instrucciones;
        this.vegano = vegano;
        this.vegetariano = vegetariano;
        this.sinGluten = sinGluten;
        this.urlFoto = urlFoto;
    }

    // Getters
    public String getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public String getInstrucciones() {
        return instrucciones;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public int getTiempo() {
        return tiempo;
    }

    public boolean isVegetariano() {
        return vegetariano;
    }

    public boolean isVegano() {
        return vegano;
    }

    public boolean isSinGluten() {
        return sinGluten;
    }




}
