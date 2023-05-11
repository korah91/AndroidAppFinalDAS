package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetallesRecetas extends AppCompatActivity {
    TextView tv_tituloReceta, tv_pasos, tv_ingredientes, tv_tiempo;
    ImageView iv_imgComida, iv_vegana, iv_vegetariana, iv_sinGluten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_recetas);

        tv_tituloReceta = findViewById(R.id.tv_tituloReceta);
        tv_pasos = findViewById(R.id.tv_pasos);
        tv_ingredientes = findViewById(R.id.tv_ingredientes);
        tv_tiempo = findViewById(R.id.tv_tiempo);
        iv_imgComida = findViewById(R.id.iv_imgComida);
        iv_vegetariana = findViewById(R.id.iv_vegetariana);
        iv_vegana = findViewById(R.id.iv_vegana);
        iv_sinGluten = findViewById(R.id.iv_gluten);

        tv_ingredientes.setMovementMethod(new ScrollingMovementMethod());
        tv_pasos.setMovementMethod(new ScrollingMovementMethod());

        Bundle extras = getIntent().getExtras();
        if (extras != null){

            String nombre = extras.getString("nombre");
            String ingredientes = extras.getString("ingredientes");
            String instrucciones = extras.getString("instrucciones");
            String urlFoto = extras.getString("urlFoto");

            String tiempo = String.valueOf(extras.getInt("tiempo"));

            boolean vegetariano = extras.getBoolean("vegetariano");
            boolean vegano = extras.getBoolean("vegano");
            boolean sinGluten = extras.getBoolean("sinGluten");

            //Rellenamos los datos de la receta
            tv_tituloReceta.setText(nombre);
            tv_pasos.setText("Instrucciones : \n\n" + instrucciones);
            tv_ingredientes.setText("Ingredientes : \n\n" + ingredientes);
            tv_tiempo.setText("Tiempo de preparación : " + tiempo + " minutos");

            Glide.with(DetallesRecetas.this).load(urlFoto).into(iv_imgComida);

            //Se comprueba si es Vegano o no para visibilizar el icono o no
            if (vegano == true){
                iv_vegana.setVisibility(View.VISIBLE);
            }
            else {
                iv_vegana.setVisibility(View.INVISIBLE);
            }
            //Se comprueba si es vegetariano o no para visibilizar el icono o no
            if (vegetariano == true){
                iv_vegetariana.setVisibility(View.VISIBLE);
            }
            else {
                iv_vegetariana.setVisibility(View.INVISIBLE);
            }
            //Se comprueba si es sinGluten o no para visibilizar el icono o no
            if (sinGluten == true){
                iv_sinGluten.setVisibility(View.VISIBLE);
            }
            else {
                iv_sinGluten.setVisibility(View.INVISIBLE);
            }

        }
    }
}
