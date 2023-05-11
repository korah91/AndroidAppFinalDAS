package com.example.proyectofinal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdaptadorListaRecetas extends RecyclerView.Adapter<ListaRecetasViewHolder> {

    private Context context;
    ArrayList<Receta> listaRecetas;
    private RecetaClickListener listener;

    /*
    private String listaTit[] = {"Macarrones", "Lentejas"};
    private String listaTiemp[] = {"10 minutos", "30 minutos"};
    private String users[] = {"Manolito", "Cooking Mama"};
    */
    private boolean[] seleccionado;



    public AdaptadorListaRecetas(Context context, RecetaClickListener listener, ArrayList<Receta> listaRecetas) {
        this.context = context;
        seleccionado = new boolean[listaRecetas.size()];
        this.listener = listener;
        this.listaRecetas = listaRecetas;

    }

    @NonNull
    @Override
    // Cuando se crea una vista se le aplica el layout
    public ListaRecetasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListaRecetasViewHolder viewHolder = new ListaRecetasViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.lista_recetas, parent, false));
        viewHolder.seleccion = seleccionado;
        return viewHolder;
    }

    // Se consiguen los datos de cada receta de la BD y se ponen en cada vista
    @Override
    public void onBindViewHolder(@NonNull ListaRecetasViewHolder holder, @SuppressLint("RecyclerView") int position) {


        // Se pone el titulo de la receta
        holder.titulo.setText("" + listaRecetas.get(position).getNombre());
        holder.titulo.setSelected(true);
        holder.tiempo_preparacion.setText("" + listaRecetas.get(position).getTiempo());
        holder.usuario_receta.setText("" + listaRecetas.get(position).getUsuario());

        Glide.with(context).load(listaRecetas.get(position).getUrlFoto()).into(holder.imagen_comida);

        // Se ejecuta cuando se da click a un elemento
        holder.recetas_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Le paso la info que necesito de la receta
                Receta recetaDetalle = listaRecetas.get(position);
                listener.onRecetaClicked(recetaDetalle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaRecetas.size();
    }

    public ArrayList<Receta> getFilteredList(ArrayList<Receta> recetas, int position) {
        ArrayList<Receta> filteredList = new ArrayList<>();
        Log.d("Posicion", "Pos en adapter --> " + position);
        listaRecetas = recetas;
        for (Receta receta : listaRecetas) {
            switch (position) {
                case 1:
                    if (receta.isVegetariano()){
                        filteredList.add(receta);
                    }
                    break;
                case 2:
                    if (receta.isVegano()){
                        filteredList.add(receta);
                    }
                    break;
                case 3:
                    if (receta.isSinGluten()) {
                        filteredList.add(receta);
                    }
                    break;
                case 0:
                        filteredList.add(receta);
                    break;
            }
        }
        Log.d("Posicion", "La lista es  --> "+ filteredList);
        return filteredList;
    }

}

// El ViewHolder controla cada vista
class ListaRecetasViewHolder extends RecyclerView.ViewHolder{
    CardView recetas_lay;
    TextView titulo, tiempo_preparacion, usuario_receta;
    ImageView imagen_comida;
    boolean[] seleccion;


    public ListaRecetasViewHolder(@NonNull View itemView){
        super(itemView);
        recetas_lay = itemView.findViewById(R.id.recetas_lay);
        titulo = itemView.findViewById(R.id.titulo);
        tiempo_preparacion = itemView.findViewById(R.id.tiempo_preparacion);
        usuario_receta = itemView.findViewById(R.id.usuario_receta);
        imagen_comida = itemView.findViewById(R.id.imagen_comida);
    }
}
