package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecetasUsuario extends AppCompatActivity {
    ArrayList<Receta> listaRec;
    private AdaptadorListaRecetas adaptadorListaRecetas;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas_usuario);

        // Descargo todas las recetas del servidor
        DBRecetas dbRecetas = new DBRecetas(RecetasUsuario.this);

        // Consigo el usuario que me ha llevado a la actividad
        Intent extras = getIntent();
        String usuario = extras.getStringExtra("nombreUsuario");

        listaRec = dbRecetas.getRecetasUsuario(usuario);
        Log.d("getRecetasUsuario", "listaRecetasLongitud " +listaRec.size());

        // Configuro el recyclerView
        recyclerView = findViewById(R.id.rv_recetas_usuario);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(RecetasUsuario.this, 1));

        // Aqui le paso la lista con las recetas
        adaptadorListaRecetas = new AdaptadorListaRecetas(RecetasUsuario.this, recetaClickListener, listaRec); //Aqui se le pasaria una lista con las recetas tambien
        recyclerView.setAdapter(adaptadorListaRecetas);


    }
    private final RecetaClickListener recetaClickListener = new RecetaClickListener() {
        @Override
        public void onRecetaClicked(Receta recetaDetalles) {
            //De aqui se abriria con un Intent la clase que mostraría los detalles de la receta
            Intent i = new Intent(RecetasUsuario.this, DetallesRecetas.class);

            // Se mandan los datos de la receta
            i.putExtra("usuario", recetaDetalles.getUsuario());
            i.putExtra("nombre", recetaDetalles.getNombre());
            i.putExtra("ingredientes", recetaDetalles.getIngredientes());
            i.putExtra("instrucciones", recetaDetalles.getInstrucciones());
            i.putExtra("urlFoto", recetaDetalles.getUrlFoto());
            i.putExtra("idReceta", recetaDetalles.getIdReceta());
            i.putExtra("tiempo", recetaDetalles.getTiempo());
            i.putExtra("vegetariano", recetaDetalles.isVegetariano());
            i.putExtra("vegano", recetaDetalles.isVegano());
            i.putExtra("sinGluten", recetaDetalles.isSinGluten());

            startActivity(i);
        }
    };


}