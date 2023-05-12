package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
    Context context;
    ArrayList<Receta> listaRec;
    boolean booleanDevolver = false;
    boolean primera = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas_usuario);
        Intent extras = getIntent();
        String usuario = extras.getStringExtra("nombreUsuario");
        DBRecetas dbRecetas = new DBRecetas(RecetasUsuario.this);
        listaRec = dbRecetas.getRecetasUsuario(usuario);
        Log.d("getRecetasUsuario", "listaRecetasLongitud " +listaRec.size());

    }

}