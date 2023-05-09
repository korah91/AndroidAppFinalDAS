package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

public class DetallesRecetas extends AppCompatActivity {
    TextView pasos, ingredientes, titulo, tiempo;
    ImageView imagenReceta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_recetas);

        titulo = findViewById(R.id.tv_tituloReceta);
        pasos = findViewById(R.id.tv_pasos);
        ingredientes = findViewById(R.id.tv_ingredientes);
        tiempo = findViewById(R.id.tv_tiempo);
        imagenReceta = findViewById(R.id.iv_imgComida);

        ingredientes.setMovementMethod(new ScrollingMovementMethod());
        pasos.setMovementMethod(new ScrollingMovementMethod());
    }
}