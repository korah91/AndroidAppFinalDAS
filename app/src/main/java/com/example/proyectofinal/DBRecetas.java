package com.example.proyectofinal;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

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

public class DBRecetas {

    Context context;
    final ArrayList<Receta> listaRecetas;
    final ArrayList<Receta> listaRec;
    boolean booleanDevolver = false;
    boolean primera = true;

    public DBRecetas(@Nullable Context context) {
        super();

        this.context = context;
        // Se crea el arrayList a devolver
        this.listaRecetas = new ArrayList<Receta>();
        this.listaRec = new ArrayList<Receta>();
    }

    public ArrayList<Receta> getRecetasGlobales(boolean vegetariano, boolean vegano, boolean sinGluten) {


        // Utilizo la libreria OkHttp
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        // Crear un objeto JSON con los campos correspondientes
        JSONObject json = new JSONObject();
        try {
            json.put("vegetariano", vegetariano);
            json.put("vegano", vegano);
            json.put("sinGluten", sinGluten);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(mediaType, json.toString());

        Log.d("getRecetasGlobales", json.toString());

        Request request = new Request.Builder()
                .url("http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/eonate006/WEB/Proyecto_final/select_Recetas_Globales.php")
                .post(body)
                .build();

        // Mientras que no se descarguen las recetas no devuelve
        while (!booleanDevolver){
            //booleanDevolver = true;
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    // Error de conexión o I/O
                    Log.d("getRecetasGlobales", "No se han podido conseguir las Recetas Globales");
                    Log.d("getRecetasGlobales", e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (response.isSuccessful()) {
                            // Solicitud exitosa
                            // Se crea el array JSON con TODAS las recetas
                            String datos = response.body().string();
                            //Log.d("Prueba_datos", "Los datos --> " + datos);

                            JSONArray jsonArray = new JSONArray(datos);
                            // Se itera sobre el JSON
                            if(primera) {
                                primera = false;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    // Se obtiene la informacion de la receta para luego guardarla en ArrayList
                                    int idReceta = jsonObject.getInt("idReceta");
                                    String nombre = jsonObject.getString("nombre");
                                    String urlFoto = jsonObject.getString("urlFoto");

                                    // Si son == 1 creamos la receta con esos valores como true, si no pues false
                                    boolean vegetariano = jsonObject.getInt("vegetariano") == 1;
                                    boolean vegano = jsonObject.getInt("vegano") == 1;
                                    boolean sinGluten = jsonObject.getInt("sinGluten") == 1;

                                    String ingredientes = jsonObject.getString("ingredientes");
                                    String instrucciones = jsonObject.getString("instrucciones");
                                    int tiempo = jsonObject.getInt("tiempo");
                                    String usuario = jsonObject.getString("usuario");

                                    // Se crea el objeto Receta
                                    Receta r = new Receta(idReceta, usuario, nombre, tiempo, ingredientes, instrucciones, vegetariano, vegano, sinGluten, urlFoto);
                                    // Se guarda en el arrayList
                                    addToListaRecetas(r);
                                }
                                booleanDevolver = true;
                            }

                        } else {
                            Log.d("getRecetasGlobales", "No se han podido conseguir las Recetas Globales");
                            Log.d("getRecetasGlobales", response.toString());
                        }
                        response.close();
                    } catch (JSONException e) {
                        Log.d("viewholder", "Ha habido excepcion" + e.toString());
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        return getListaRecetas();
    }

    public ArrayList<Receta> getRecetasUsuario(String usuario) {

        // Utilizo la libreria OkHttp
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");

        // Crear un objeto JSON con los campos correspondientes
        JSONObject json = new JSONObject();
        try {
            json.put("usuario", usuario);
        } catch (JSONException e) {
            Log.d("getRecetasUsuario", "excepcion");
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(mediaType, json.toString());

        Log.d("getRecetasUsuario", json.toString());

        Request request = new Request.Builder()
                .url("http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/eonate006/WEB/Proyecto_final/select_Recetas_Usuario.php")
                .post(body)
                .build();
        // Mientras que no se descarguen las recetas no devuelve
        while (!booleanDevolver){
            //booleanDevolver = true;
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    // Error de conexión o I/O
                    Log.d("getRecetasUsuario", "No se han podido conseguir las Recetas");
                    Log.d("getRecetasUsuario", e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (response.isSuccessful()) {
                            // Solicitud exitosa
                            // Se crea el array JSON con TODAS las recetas
                            String datos = response.body().string();

                            JSONArray jsonArray = new JSONArray(datos);
                            // Se itera sobre el JSON
                            if(primera) {
                                primera = false;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    // Se obtiene la informacion de la receta para luego guardarla en ArrayList
                                    int idReceta = jsonObject.getInt("idReceta");
                                    String nombre = jsonObject.getString("nombre");
                                    String urlFoto = jsonObject.getString("urlFoto");

                                    // Si son == 1 creamos la receta con esos valores como true, si no pues false
                                    boolean vegetariano = jsonObject.getInt("vegetariano") == 1;
                                    boolean vegano = jsonObject.getInt("vegano") == 1;
                                    boolean sinGluten = jsonObject.getInt("sinGluten") == 1;

                                    String ingredientes = jsonObject.getString("ingredientes");
                                    String instrucciones = jsonObject.getString("instrucciones");
                                    int tiempo = jsonObject.getInt("tiempo");
                                    String usuario = jsonObject.getString("usuario");

                                    // Se crea el objeto Receta
                                    Receta r = new Receta(idReceta, usuario, nombre, tiempo, ingredientes, instrucciones, vegetariano, vegano, sinGluten, urlFoto);
                                    // Se guarda en el arrayList
                                    addToListaRecetas(r);
                                }
                                booleanDevolver = true;
                            }

                        } else {
                            Log.d("getRecetasUsuario", "No se han podido conseguir las Recetas Globales");
                            Log.d("getRecetasUsuario", response.toString());
                        }
                        response.close();
                    } catch (JSONException e) {
                        Log.d("viewholder", "Ha habido excepcion" + e.toString());
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        return getListaRecetas();
    }

    public void addToListaRecetas(Receta r){
        listaRecetas.add(r);
    }

    public ArrayList<Receta> getListaRecetas(){
        return this.listaRecetas;
    }

}
