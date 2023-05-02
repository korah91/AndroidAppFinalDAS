package com.example.proyectofinal;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_opciones, container, false);
        ImageView mapas = (ImageView) view.findViewById(R.id.opcion1);
        ImageView robot = (ImageView) view.findViewById(R.id.opcion2);
        mapas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapas();
            }
        });
        robot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expertoIA();
            }
        });

        return view;
    }

    public void mapas(){
        Intent map = new Intent(getContext(), Mapas.class);
        startActivity(map);
    }

    public void expertoIA(){
        Intent ia = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.chatpdf.com/share/iLS1v2wbxppw50l6OtR5G"));
        startActivity(ia);
    }
}