package com.example.huelvapedia;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MiViewHolder> implements View.OnClickListener
{
    private Context context;
    private ArrayList<Elementos> elementos;
    private View.OnClickListener listener;

    public Adaptador(Context context, ArrayList<Elementos> elementos)
    {
        this.context = context;
        this.elementos = elementos;
    }

    @NonNull
    @Override
    public MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view = LayoutInflater.from(context).inflate(R.layout.principal, parent, false);
       view.setOnClickListener(this);
       return new MiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewHolder holder, int position)
    {
        Elementos elemento = elementos.get(position);

        holder.nombre.setText(elemento.getNombre());
        holder.descripcion.setText(elemento.getDescripcion());
        Picasso.get().load(elemento.getFoto()).into(holder.imagen);

        boolean tieneUbicacion = elemento.getUbicacion1() != null && elemento.getUbicacion2() != null;
        boolean tieneTelefono = elemento.getTelefono() != null;
        boolean tieneEnlace = elemento.getEnlace() != null;

        if (!elemento.getUltimo()) // Si no es el último, se navega a la Activity Secundaria
        {
            holder.botonUbicacion.setVisibility(View.GONE);
            holder.botonLlamar.setVisibility(View.GONE);
            holder.botonEnlace.setVisibility(View.GONE);

            if (!tieneUbicacion && !tieneTelefono && !tieneEnlace)
            {
                holder.itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(v.getContext(), Secundaria.class);
                    intent.putExtra("Nombre", elemento.getNombre());
                    v.getContext().startActivity(intent);
                });
            }
        }
        else // Es el último elemento, solo permite pulsar botones de acción
        {
            // Botón de ubicación (mapa)
            if (tieneUbicacion)
            {
                holder.botonUbicacion.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(elemento.getUbicacion1() + Uri.encode(elemento.getUbicacion2())));
                    Intent chooser = Intent.createChooser(intent, "Launch Maps");
                    v.getContext().startActivity(chooser);
                });
            }
            else
            {
                holder.botonUbicacion.setVisibility(View.GONE);
            }

            // Botón de teléfono (llamar)
            if (tieneTelefono)
            {
                holder.botonLlamar.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + elemento.getTelefono()));
                    v.getContext().startActivity(intent);
                });
            }
            else
            {
                holder.botonLlamar.setVisibility(View.GONE);
            }

            // Botón de enlace (web)
            if (tieneEnlace)
            {
                holder.botonEnlace.setOnClickListener(v -> {
                    Uri link = Uri.parse(elemento.getEnlace());
                    Intent intent = new Intent(Intent.ACTION_VIEW, link);
                    v.getContext().startActivity(intent);
                });
            }
            else
            {
                holder.botonEnlace.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return elementos.size();
    }

    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null)
        {
            listener.onClick(v);
        }
    }

    static class MiViewHolder extends RecyclerView.ViewHolder
    {
        TextView nombre, descripcion;
        ImageView imagen;
        Button botonUbicacion, botonLlamar, botonEnlace;

        public MiViewHolder(@NonNull View itemView)
        {
            super(itemView);

            nombre = itemView.findViewById(R.id.tenombre);
            descripcion = itemView.findViewById(R.id.tedescri);
            imagen = itemView.findViewById(R.id.imagen);
            botonUbicacion = itemView.findViewById(R.id.botonubi);
            botonLlamar = itemView.findViewById(R.id.botonllamar);
            botonEnlace = itemView.findViewById(R.id.botonwiki);
        }
    }
}
