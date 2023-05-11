package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Comunidad#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Comunidad extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AdaptadorListaRecetas adaptadorListaRecetas;
    private RecyclerView recyclerView;

    public Comunidad() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecetasLocal.
     */
    // TODO: Rename and change types and number of parameters
    public static Comunidad newInstance(String param1, String param2) {
        Comunidad fragment = new Comunidad();
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

        View view = inflater.inflate(R.layout.fragment_comunidad, container, false);

        // Descargo todas las recetas del servidor
        DBRecetas dbRecetas = new DBRecetas(this.getContext());
        // Las cargo en un arrayList
        ArrayList<Receta> listaRecetas = dbRecetas.getRecetasGlobales(false,false,false);
        //Log.d("viewholder", "Comunidad:"+listaRecetas);

        // Configuro el recyclerView
        recyclerView = view.findViewById(R.id.rv_recetas_comunidad);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        // Aqui le paso la lista con las recetas
        adaptadorListaRecetas = new AdaptadorListaRecetas(getActivity(), recetaClickListener, listaRecetas); //Aqui se le pasaria una lista con las recetas tambien
        recyclerView.setAdapter(adaptadorListaRecetas);

        // Inflate the layout for this fragment
        return view;
    }

    private final RecetaClickListener recetaClickListener = new RecetaClickListener() {
        @Override
        public void onRecetaClicked(Receta recetaDetalles) {
            //De aqui se abriria con un Intent la clase que mostraría los detalles de la receta
            Intent i = new Intent(getContext(), DetallesRecetas.class);

            // Se mandan los datos de la receta
            i.putExtra("usuario", recetaDetalles.getUsuario());
            i.putExtra("nombre", recetaDetalles.getNombre());
            i.putExtra("ingredientes", recetaDetalles.getIngredientes());
            i.putExtra("instrucciones", recetaDetalles.getInstrucciones());
            i.putExtra("urlFoto", recetaDetalles.getUrlFoto());
            i.putExtra("idReceta", recetaDetalles.getIdReceta());
            i.putExtra("tiempo", recetaDetalles.getTiempo());
            i.putExtra("vegetariano", recetaDetalles.isVegetariano());
            i.putExtra("vegano", recetaDetalles.isVegano());
            i.putExtra("sinGluten", recetaDetalles.isSinGluten());

            startActivity(i);
        }
    };
}