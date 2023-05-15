package com.example.proyectofinal;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class SelectCredenciales extends Worker {
    public SelectCredenciales(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        //Datos enviados desde InicioSesionActivity
        String username = getInputData().getString("usuario");
        String password = getInputData().getString("password");

        //Se conecta con el servidor
        String direccion = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/eonate006/WEB/Proyecto_final/select_BD_DAS.php";
        HttpURLConnection urlConnection = null;
        try {
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);

            //Rellenamos los parametros
            String parametros = "usuario=" + username + "&password=" + password;

            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            //Enviar los parametros al php
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(parametros);
            //Agregar esta línea para asegurarse de que los datos se envíen correctamente
            out.flush();

            int status = urlConnection.getResponseCode();
            Log.d("Prueba_Select", "Status --> " + status);

            // En caso de obtener 200
            if (status == 200) {
                BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line, result = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                inputStream.close();

                Data data = new Data.Builder()
                        .putString("result", result)
                        .build();
                // Se devuelve la respuesta
                return Result.success(data);
            }
        } catch (Exception e) {
            Log.d("conexion", "Error: " + e);
        }
        return Result.failure();
    }
}