package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetallesRecetas extends AppCompatActivity {
    TextView tv_tituloReceta, tv_pasos, tv_ingredientes, tv_tiempo;
    ImageView iv_imgComida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_recetas);

        tv_tituloReceta = findViewById(R.id.tv_tituloReceta);
        tv_pasos = findViewById(R.id.tv_pasos);
        tv_ingredientes = findViewById(R.id.tv_ingredientes);
        tv_tiempo = findViewById(R.id.tv_tiempo);
        iv_imgComida = findViewById(R.id.iv_imgComida);

        tv_ingredientes.setMovementMethod(new ScrollingMovementMethod());
        tv_pasos.setMovementMethod(new ScrollingMovementMethod());

        Bundle extras = getIntent().getExtras();
        if (extras != null){

            String nombre = extras.getString("nombre");
            String ingredientes = extras.getString("ingredientes");
            String instrucciones = extras.getString("instrucciones");
            String urlFoto = extras.getString("urlFoto");
            int idReceta = Integer.parseInt(extras.getString("idReceta"));

            String tiempo = extras.getString("tiempo");

            boolean vegetariano = extras.getBoolean("vegetariano");
            boolean vegano = extras.getBoolean("vegano");
            boolean sinGluten = extras.getBoolean("sinGluten");

            tv_tituloReceta.setText(nombre);
            tv_pasos.setText(instrucciones);
            tv_ingredientes.setText(ingredientes);
            tv_tiempo.setText(tiempo);

            Glide.with(DetallesRecetas.this).load(urlFoto).into(iv_imgComida);
            //iv_imgComida.setImageURI(nombre);

            // VEGANO VEGETARIANO ICONOS @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


        }
    }
}
