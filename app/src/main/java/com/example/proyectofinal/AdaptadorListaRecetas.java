package com.example.proyectofinal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptadorListaRecetas extends RecyclerView.Adapter<ListaRecetasViewHolder> {

    private Context context;
    //List<Recipe> list;
    private String listaTit[] = {"Macarrones", "Lentejas"};
    private String listaTiemp[] = {"10 minutos", "30 minutos"};
    private String users[] = {"Manolito", "Cooking Mama"};
    private boolean[] seleecionado;
    private RecetaClickListener listener;

    public AdaptadorListaRecetas(Context context, RecetaClickListener listener) {
        this.context = context;
        seleecionado = new boolean[listaTit.length];
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListaRecetasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListaRecetasViewHolder viewHolder = new ListaRecetasViewHolder(LayoutInflater.from(context).inflate(R.layout.lista_recetas, parent, false));
        viewHolder.seleccion = seleecionado;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListaRecetasViewHolder holder, int position) {
        holder.titulo.setText(listaTit[position]);
        holder.titulo.setSelected(true);
        //holder.num_likes.setText("20 likes");
        holder.tiempo_preparacion.setText(listaTiemp[position]);
        holder.usuario_receta.setText(users[position]);
       /* holder.titulo.setText(list.get(position).title);
        holder.titulo.setSelected(true);
        holder.num_likes.setSelected(list.get(position).aggregateLikes+ " Likes");
        holder.tiempo_preparacion.setText(list.get(position).time+ " Minutos");
        holder.usuario_receta.setText(list.get(position).usuario);
        Picasso.get().load(list.get(position).image.into(holder.imagen_comida))*/
        Glide.with(context).load(R.drawable.comidapordefecto).into(holder.imagen_comida);

        holder.recetas_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Le paso la info que necesito de la receta
                String usuario = String.valueOf(users[holder.getAdapterPosition()]);
                String receta = String.valueOf(listaTit[holder.getAdapterPosition()]);
                listener.onRecetaClicked(usuario, receta);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 2;//list.size()
    }
}
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
