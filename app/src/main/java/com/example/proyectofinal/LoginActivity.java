package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    }

    public void invitado(View view){

    }
}