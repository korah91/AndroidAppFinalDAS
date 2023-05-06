package com.example.proyectofinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptadorListaRecetas extends RecyclerView.Adapter<ListaRecetasViewHolder> {

    Context context;
    //List<Recipe> list;
    String listaTit[] = {"macarrones", "lentejas"};

    public AdaptadorListaRecetas(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ListaRecetasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListaRecetasViewHolder(LayoutInflater.from(context).inflate(R.layout.lista_recetas, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListaRecetasViewHolder holder, int position) {
        holder.titulo.setText(listaTit[position]);
        holder.titulo.setSelected(true);
        //holder.num_likes.setText("20 likes");
        holder.tiempo_preparacion.setText("10 minutos");
       /* holder.titulo.setText(list.get(position).title);
        holder.titulo.setSelected(true);
        holder.num_likes.setSelected(list.get(position).aggregateLikes+ " Likes");
        holder.tiempo_preparacion.setText(list.get(position).time+ " Minutos");
        Picasso.get().load(list.get(position).image.into(holder.imagen_comida))*/
        Glide.with(context).load(R.drawable.comidapordefecto).into(holder.imagen_comida);
    }

    @Override
    public int getItemCount() {
        return 2;//list.size()
    }
}
class ListaRecetasViewHolder extends RecyclerView.ViewHolder{
    CardView recetas_lay;
    TextView titulo, tiempo_preparacion, num_likes;
    ImageView imagen_comida;

    public ListaRecetasViewHolder(@NonNull View itemView){
        super(itemView);
        recetas_lay = itemView.findViewById(R.id.recetas_lay);
        titulo = itemView.findViewById(R.id.titulo);
        tiempo_preparacion = itemView.findViewById(R.id.tiempo_preparacion);
        //num_likes = itemView.findViewById(R.id.num_likes);
        imagen_comida = itemView.findViewById(R.id.imagen_comida);
    }
}
