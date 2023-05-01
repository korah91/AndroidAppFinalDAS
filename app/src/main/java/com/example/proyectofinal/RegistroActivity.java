package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }

    public void registro(View view){
        EditText user_UI = findViewById(R.id.mail_text_regis);
        EditText pass_UI = findViewById(R.id.password_text_regis);
        EditText pass_2_UI = findViewById(R.id.password_text_regis_2)

        String user = user_UI.getText().toString().toLowerCase();
        String pass = pass_UI.getText().toString();
        String pass2 = pass_2_UI.getText().toString();

        //Comprobación del usuario
        if (!user.isEmpty() && !pass.isEmpty() && !pass2.isEmpty() && pass.equals(pass2)){
            Log.d("Registro", "Usuario --> " + user + "\nPass --> " + pass);
            finish();
        }
        else{
            Toast.makeText(RegistroActivity.this, "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}