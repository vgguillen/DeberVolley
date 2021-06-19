package com.example.debervolley;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    private List<RevistaModel> Revistalista;
    // context que vendrá desde el activity que se va a implementar
    private Context contexto;

    public Adapter(List<RevistaModel> revistalista, Context contexto) {
        this.Revistalista = revistalista;
        this.contexto = contexto;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_elementos,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
       holder.titulorevista.setText(Revistalista.get(position).getTitle());
       holder.fechapublicacionrevista.setText(Revistalista.get(position).getDate_published());
       holder.volumenrevista.setText(Revistalista.get(position).getVolume());
        Glide.with(contexto)
                             .load(Revistalista.get(position).getCover())
                              .centerCrop()
                              .into(holder.fotorevistaimg);
    }

    @Override
    public int getItemCount() {
        return Revistalista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView fotorevistaimg;
        private TextView titulorevista;
        private TextView fechapublicacionrevista;
        private TextView volumenrevista;


        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            fotorevistaimg = itemView.findViewById(R.id.img);
            titulorevista = itemView.findViewById(R.id.Titulorevistatxt);
            fechapublicacionrevista = itemView.findViewById(R.id.Fecharevistatxt);
            volumenrevista = itemView.findViewById(R.id.VolumenRevistatxt);
        }
    }


}
