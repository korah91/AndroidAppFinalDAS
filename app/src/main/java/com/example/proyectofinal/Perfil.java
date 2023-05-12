package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Perfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Perfil extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView usuario;
    private String nombreUsuario;
    private Button bt_verRecetas, bt_editarDatos, bt_logOut;

    public Perfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * //@param param2 Parameter 2.
     * @return A new instance of fragment perfil.
     */
    // TODO: Rename and change types and number of parameters
    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_perfil, container, false);
        usuario = (TextView) view.findViewById(R.id.tv_usuario);
        bt_logOut = (Button) view.findViewById(R.id.button_logout);
        bt_verRecetas = (Button) view.findViewById(R.id.button_ver_recetas);

        //Leer usuario del fichero
        try {
            BufferedReader ficherointerno = new BufferedReader(new InputStreamReader(
                    getActivity().openFileInput("sesion.txt")));
            String linea = ficherointerno.readLine();
            ficherointerno.close();

            //Comprobamos si es un usuario validado o es invitado
            if (!linea.equals("-1")){
                usuario.setText("Bienvenido " + linea.toUpperCase());
                nombreUsuario = linea;
            }
            else {
                usuario.setText("INVITADO");
                bt_verRecetas.setVisibility(View.INVISIBLE);
                bt_logOut.setText("Iniciar Sesión");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Cuando pulsa en el botón de "cerrar sesión" || iniciar sesión en el caso de invitado --> Redirige a la pantalla de inicio sesión
        bt_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finishAffinity();
            }
        });

        //Botón para ver las recetas subidas por el usuario
        bt_verRecetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recetasUsuario = new Intent(getActivity(), RecetasUsuario.class);
                recetasUsuario.putExtra("nombreUsuario", nombreUsuario);
                Log.d("Comprobar_Usuario", "usuario --> " + nombreUsuario);

                startActivity(recetasUsuario);
            }
        });

        return view;
    }

}