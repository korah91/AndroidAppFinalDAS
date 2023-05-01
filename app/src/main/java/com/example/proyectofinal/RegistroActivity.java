package com.example.proyectofinal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;


public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }

    public void registro(View view){
        EditText user_UI = findViewById(R.id.mail_text_regis);
        EditText pass_UI = findViewById(R.id.password_text_regis);
        EditText pass_2_UI = findViewById(R.id.password_text_regis_2);

        String user = user_UI.getText().toString().toLowerCase();
        String pass = pass_UI.getText().toString();
        String pass2 = pass_2_UI.getText().toString();

        //Comprobación del usuario
        if (!user.isEmpty() && !pass.isEmpty() && !pass2.isEmpty()){
            if (pass.equals(pass2)){
                comprobarUsuario(user, pass);
            }
            else {
                Toast.makeText(RegistroActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(RegistroActivity.this, "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void comprobarUsuario(String user, String pass){
        final boolean[] existe = {false};
        Data data = new Data.Builder()
                .putString("usuario", user).build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(SelectCredenciales.class)
                .setInputData(data).build();
        WorkManager.getInstance(RegistroActivity.this).enqueue(otwr);

        WorkManager.getInstance(RegistroActivity.this).getWorkInfoByIdLiveData(otwr.getId()).observe(RegistroActivity.this, new Observer<WorkInfo>() {
            public void onChanged(@Nullable WorkInfo workInfo) {
                if (workInfo != null && workInfo.getState().isFinished()) {
                    String[] lista = workInfo.getOutputData().getStringArray("array"); //Se recoge la respuesta del servidor (Array[])
                    if (lista != null){
                        existe[0] = lista[0].equals(user);
                    }
                    else{
                        existe[0] = false;
                    }
                    if (existe[0] == true){
                        Toast.makeText(RegistroActivity.this, "El usuario ya existe", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Data data = new Data.Builder()
                                .putString("usuario",user).putString("password", pass).build();

                        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(ConexionBDWebService.class)
                                .setInputData(data)
                                .build();
                        WorkManager.getInstance(RegistroActivity.this).enqueue(otwr);
                        finish();
                    }
                }
            }
        });
    }
}