package com.example.proyectofinal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Opciones#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Opciones extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean activarDialogPreferencias, activarDialogTipoRecetas;
    private String iaSeleccionada;

    public Opciones() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Opciones.
     */
    // TODO: Rename and change types and number of parameters
    public static Opciones newInstance(String param1, String param2) {
        Opciones fragment = new Opciones();
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

        if (savedInstanceState != null){
            activarDialogPreferencias = savedInstanceState.getBoolean("preferencias");
            activarDialogTipoRecetas = savedInstanceState.getBoolean("tipoRecetas");
        }

        if (activarDialogTipoRecetas == true){
            activarDialogTipoRecetas();
        }

        if (activarDialogPreferencias == true){
            activarDialogPreferencias();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_opciones, container, false);
        ImageView mapas = (ImageView) view.findViewById(R.id.opcion1);
        TextView mapastx = (TextView) view.findViewById(R.id.supermarket);
        ImageView robot = (ImageView) view.findViewById(R.id.opcion2);
        TextView robottx = (TextView) view.findViewById(R.id.IA);
        ImageView random = (ImageView) view.findViewById(R.id.opcion3);
        TextView randomtx = (TextView) view.findViewById(R.id.ruleta);
        //Abre la pantalla de mapas al pulsar la imagen del carrito de la compra
        mapas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapas();
            }
        });
        //Abre la pantalla de mapas al pulsar las letras de supermercados
        mapastx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { mapas();}
        });
        //Se activa el dialogo de recetas al pulsar la imagen del robot
        robot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activarDialogTipoRecetas();
            }
        });
        //Se activa el dialogo de recetas al pulsar las letras de recetas
        robottx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { activarDialogTipoRecetas(); }
        });
        //Abre la pantalla de la ruleta al pulsar la imagen de la interrogacion
        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { recetaRandom();}
        });
        //Abre la pantalla de la ruleta al pulsar las letras de recetas aleatorias
        randomtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { recetaRandom(); }
        });

        return view;
    }

    private void mapas(){
        Intent map = new Intent(getContext(), Mapas.class);
        startActivity(map);
    }

    private void expertoIA(String iaSeleccionada){
        if (iaSeleccionada.equals("Tradicional Española")){
            //Cocina española
            Intent spain = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.chatpdf.com/share/iLS1v2wbxppw50l6OtR5G"));
            startActivity(spain);
        }
        else if (iaSeleccionada.equals("Postres")){
            //Postres
            Intent postres = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.chatpdf.com/share/SQrD53QoNBvALFSRF5fSg"));
            startActivity(postres);
        }
        else if (iaSeleccionada.equals("Oriental")){
            //Cocina oriental
            Intent oriental = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.chatpdf.com/share/7jRqbYT3duujMaFTOmW7U"));
            startActivity(oriental);
        }
    }

    private void recetaRandom(){
        activarDialogPreferencias();
    }

    private void activarDialogPreferencias(){
        activarDialogPreferencias = true;
        AlertDialog.Builder builderPreferencias = new AlertDialog.Builder(getActivity());
        builderPreferencias.setTitle("Elige tus preferencias");
        final CharSequence[] opciones = {"Vegano", "Vegetariano", "Sin Glúten"};
        final ArrayList<CharSequence> elegidos = new ArrayList<>();
        builderPreferencias.setMultiChoiceItems(opciones, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked == true){
                    //Se añaden los strings
                    elegidos.add(opciones[which]);
                }
                else if (elegidos.contains(opciones[which])){
                    elegidos.remove(opciones[which]);
                }
            }
        });
        builderPreferencias.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent random = new Intent(getContext(), RecetaRandom.class);
                Log.d("Prueba", "Elegidos --> " + elegidos);
                random.putExtra("preferencias",elegidos);
                startActivity(random);
                //Para que cuando retrocedas de la actividad de la ruleta no se active solo
                activarDialogPreferencias = false;
            }
        });

        builderPreferencias.setCancelable(false);
        builderPreferencias.show();
    }

    private void activarDialogTipoRecetas(){
        activarDialogTipoRecetas = true;
        AlertDialog.Builder builderRobot = new AlertDialog.Builder(getActivity());
        builderRobot.setTitle("Elige el experto que desees");
        final CharSequence[] opciones = {"Tradicional Española", "Postres", "Oriental"};
        builderRobot.setSingleChoiceItems(opciones, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                iaSeleccionada = opciones[which].toString();
            }
        });
        builderRobot.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (iaSeleccionada != null){
                    expertoIA(iaSeleccionada);
                }
                else{
                    Toast.makeText(getActivity(), "Debes seleccionar alguna opción", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builderRobot.setCancelable(false);
        builderRobot.show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("preferencias", activarDialogPreferencias);
        outState.putBoolean("tipoRecetas", activarDialogTipoRecetas);
    }
}