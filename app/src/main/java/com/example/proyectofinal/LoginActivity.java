package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void registro(View view){
        Intent registro = new Intent(LoginActivity.this, RegistroActivity.class);
        startActivity(registro);
    }

    public void inicioSesion(View view){
        EditText user_text = findViewById(R.id.mail_text);
        EditText psw_text = findViewById(R.id.password_text);

        String user = user_text.getText().toString().toLowerCase();
        String pass = psw_text.getText().toString();

        Log.d("Prueba_Login", "usuarioo --> " + user + "\nPass --> " + pass);


        if (user.isEmpty() || pass.isEmpty()){
            Toast.makeText(LoginActivity.this, "Debe rellenar todos los campos", Toast.LENGTH_LONG).show();
        }
        else {
            Data data = new Data.Builder()
                    .putString("usuario", user).build();
            OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(SelectCredenciales.class)
                    .setInputData(data).build();
            WorkManager.getInstance(LoginActivity.this).getWorkInfoByIdLiveData(otwr.getId()).observe(LoginActivity.this, new Observer<WorkInfo>() {
                @Override
                public void onChanged(WorkInfo workInfo) {
                    if (workInfo != null && workInfo.getState().isFinished()) {
                        String[] lista = workInfo.getOutputData().getStringArray("array");
                        Log.d("Inicio_Prueba", "lista[0] --> " + lista[0]);
                        if (lista[0].equals(user) && lista[1].equals(pass)) {
                            Intent inicio = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(inicio);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Tu usuario o contraseña son incorrectos", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
            WorkManager.getInstance(LoginActivity.this).enqueue(otwr);
        }
    }

    public void invitado(View view){
        Intent inicio = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(inicio);
    }
}