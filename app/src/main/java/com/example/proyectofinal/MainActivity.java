package com.example.proyectofinal;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Presentation;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    //private String urlChat = "https://www.google.com";
    //AdaptadorListaRecetas adaptadorListaRecetas;
    //RecyclerView recyclerView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recyclerView = findViewById(R.id.recycler_lista);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
        //adaptadorListaRecetas = new AdaptadorListaRecetas(MainActivity.this); //Aqui se le pasaria una lista con las recetas tambien
        //recyclerView.setAdapter(adaptadorListaRecetas);

        //Usuario de LoginActivity
        Bundle extras = getIntent().getExtras();
        String usuario = extras.getString("user");

        Bundle result = new Bundle();
        result.putString("usuario", usuario);
        getSupportFragmentManager().setFragmentResult("user", result);
        Perfil perfil = new Perfil();
        perfil.setArguments(result);

        // Agregar el fragmento a la actividad
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, perfil)
                .commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }

    // Metodo para mostrar TOAST. No funciona si no
    public void showToast(final String toast)
    {
        runOnUiThread(() -> Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show());
    }

}