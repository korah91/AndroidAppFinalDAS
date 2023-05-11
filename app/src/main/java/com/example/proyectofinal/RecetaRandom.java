package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

public class RecetaRandom extends AppCompatActivity {

    ArrayList<Receta> listaRecetas = new ArrayList<Receta>();

        //Codigo obtenido de --> https://youtu.be/y1fptOfsIRs : Autor --> TechPot
        //Valores de la ruleta
        final String[] sectors = {"rosa", "naranja", "azul", "verde", "rosa", "naranja", "azul", "verde"};
        final int[] sectorsDegrees = new int[sectors.length];
        String recetaSeleccionada;

        //Indice Aleatorio
        int randomSectorIndex, numTirada, part;

        //Lo que va a GIRAR
        ImageView wheel;
        boolean girando, dialogOn;

        //Generar la aleatoriedad
        Random random = new Random();

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_receta_random);

            //Se comprueban las preferencias
            Bundle extrasPreferencias = getIntent().getExtras();
            if (extrasPreferencias != null){
                ArrayList<String> listaPreferencias = extrasPreferencias.getStringArrayList("preferencias");
                if (listaPreferencias.size() != 0){
                    Log.d("Prueba_Preferencias", "Preferencias --> " + listaPreferencias.get(0));
                    if (listaPreferencias.size() == 3){
                        Toast.makeText(this, "Preferencias : \n- " + listaPreferencias.get(0) + "\n- " + listaPreferencias.get(1) + "\n- " + listaPreferencias.get(2) , Toast.LENGTH_SHORT).show();
                    }
                    else if (listaPreferencias.size() == 2){
                        Toast.makeText(this, "Preferencias : \n- " + listaPreferencias.get(0) + "\n- " + listaPreferencias.get(1), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Preferencias : " + listaPreferencias.get(0), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    DBRecetas dbRecetas = new DBRecetas(RecetaRandom.this);
                    listaRecetas = dbRecetas.getRecetasGlobales(false, false, false);
                    Toast.makeText(this, "No hay preferencias", Toast.LENGTH_SHORT).show();
                }
            }

            //Gestion del giro de pantalla para que se mantenga la animacion
            if (savedInstanceState != null) {
                girando = savedInstanceState.getBoolean("giro");
                dialogOn = savedInstanceState.getBoolean("dialogo");
                part = savedInstanceState.getInt("seleccionado");
            }

            if (dialogOn) {
                activarDialog(listaRecetas.get(part));
            }

            //Lo que gira
            wheel = findViewById(R.id.iv_wheel);

            //Generar los grados de cada sector
            generateSectorDegrees();

            //Click en el boton para que gire
            Button spin = findViewById(R.id.btn_spin);
            spin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Solo si no gira
                    if (!girando) {
                        girar();
                        girando = true;
                        numTirada++;
                    }
                }
            });

        }

        private void girar () {
            //Obetener cualquier indice aleatorio
            double randomIndex = Math.random() * sectors.length;
            if (Math.round(randomIndex) == 8) {
                randomIndex = 6.8;
            }
            randomSectorIndex = (int) Math.round(randomIndex);

            //Generar un grado aleatorio
            int randomDegree = generateRandomDegreeToSpin();

            //Animacion de rotacion
            RotateAnimation rotateAnimation = new RotateAnimation(0, randomDegree,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);

            //Tiempo
            rotateAnimation.setDuration(3600); //3.6 sec
            rotateAnimation.setFillAfter(true);

            //Interpolator
            rotateAnimation.setInterpolator(new DecelerateInterpolator()); //Rapido al inicio, despacio al final

            //Spinning Listener
            rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation){}

                @Override
                public void onAnimationEnd(Animation animation) {
                    //Para cuando el giro pare
                    //Participante aleatorio

                    double partRandom = Math.random() * listaRecetas.size();
                    if (Math.round(partRandom) == listaRecetas.size()) {
                        partRandom = listaRecetas.size() - 1.2;
                    }
                    part = (int) Math.round(partRandom);

                    //Fin Giro
                    girando = false;

                    //Recogemos la receta
                    recetaSeleccionada = listaRecetas.get(part).getNombre();
                    //Alerta de dialogo para participar en un doble o nada
                    activarDialog(listaRecetas.get(part));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            //Aplicar la animacion a la imagen
            wheel.startAnimation(rotateAnimation);

        }

        private int generateRandomDegreeToSpin () {
            //Un numero lo mas grande posible
            return (360 * sectors.length) + sectorsDegrees[randomSectorIndex];
        }

        private void generateSectorDegrees () {
            //Sector 1
            int sectorDegree = 360 / sectors.length;

            for (int i = 0; i < sectors.length; i++) {
                sectorsDegrees[i] = (i + 1) * sectorDegree;
            }
        }

        private void activarDialog (Receta recetaSeleccionada){
            dialogOn = true;
            AlertDialog.Builder builder = new AlertDialog.Builder(RecetaRandom.this);
            builder.setTitle("Receta Seleccionado : " + recetaSeleccionada.getNombre());
            builder.setMessage("¿Quieres ver la receta?");
            builder.setCancelable(false);

            builder.setPositiveButton(" ¡Claro que sí!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialogOn = false;
                    Intent recetaData = new Intent(RecetaRandom.this, DetallesRecetas.class);
                    // Se mandan los datos de la receta
                    recetaData.putExtra("usuario", recetaSeleccionada.getUsuario());
                    recetaData.putExtra("nombre", recetaSeleccionada.getNombre());
                    recetaData.putExtra("ingredientes", recetaSeleccionada.getIngredientes());
                    recetaData.putExtra("instrucciones", recetaSeleccionada.getInstrucciones());
                    recetaData.putExtra("urlFoto", recetaSeleccionada.getUrlFoto());
                    recetaData.putExtra("idReceta", recetaSeleccionada.getIdReceta());
                    recetaData.putExtra("tiempo", recetaSeleccionada.getTiempo());
                    recetaData.putExtra("vegetariano", recetaSeleccionada.isVegetariano());
                    recetaData.putExtra("vegano", recetaSeleccionada.isVegano());
                    recetaData.putExtra("sinGluten", recetaSeleccionada.isSinGluten());
                    //Iniciamos la actividad
                    startActivity(recetaData);
                    finish();
                }
            });

            builder.setNegativeButton("No me apetece ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialogOn = false;
                    dialog.cancel();
                }
            });

            builder.show();
        }

        //Guardamos los valores para poder gestionar el giro de pnatalla
        protected void onSaveInstanceState (@NonNull Bundle outState){
            super.onSaveInstanceState(outState);
            outState.putBoolean("giro", girando);
            outState.putBoolean("dialogo", dialogOn);
            outState.putInt("seleccionado", part);
        }
}