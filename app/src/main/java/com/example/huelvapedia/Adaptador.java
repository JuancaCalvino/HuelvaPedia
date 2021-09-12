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

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MiViewHolder> implements View.OnClickListener
{
    Context context;
    ArrayList<Elementos> elementos; //Arraylist que almacena los elementos que serán mostrados
    private View.OnClickListener listener;

    public Adaptador(Context c, ArrayList<Elementos> e)
    {
        context = c;
        elementos = e; //Se declara el ArrayList de elementos en el adapatador
    }

    @NonNull
    @Override
    public MiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view = LayoutInflater.from(context).inflate(R.layout.principal,parent,false);
       view.setOnClickListener(this);
       return new MiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiViewHolder holder, int position)
    {
        holder.nombre.setText(elementos.get(position).getNombre());                 //Se pone el nombre del tema actual en el TextView
        holder.descripcion.setText(elementos.get(position).getDescripcion());       //Se pone la descripción del tema actual en el TextView
        Picasso.get().load(elementos.get(position).getFoto()).into(holder.imagen);  //Se pone la imagen del tema actual en el ImageView, para ello se hace uso de la librería picasso

        if (elementos.get(position).getUltimo() == false) //Si no es el ultimo solo debe abrirse y ocultar botones
        {
            if((elementos.get(position).getUbicacion1() == null) && (elementos.get(position).getUbicacion2() == null) && (elementos.get(position).getTelefono() == null) && (elementos.get(position).getEnlace() == null)) //Si no se puede ni llamar ni ver mapa ni ver enlace
            {
                //Se ocultan los botones de ubicación, teléfono y enlace
                holder.boton.setVisibility(View.GONE);
                holder.boton1.setVisibility(View.GONE);
                holder.boton2.setVisibility(View.GONE);

                holder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) //Envío a la segunda actividad "Secundaria" el nombre para poder buscar su referencia en la que se haga click
                    {
                        Intent intent = new Intent(v.getContext(),Secundaria.class);
                        intent.putExtra("Nombre", elementos.get(position).getNombre());
                        v.getContext().startActivity(intent);
                    }
                });
            }
        }
        else //Si es el ultimo elemento solo debe dejar pulsar botones
        {
            if((elementos.get(position).getUbicacion1() == null) && (elementos.get(position).getUbicacion2() == null) && (elementos.get(position).getTelefono() == null) && (elementos.get(position).getEnlace() == null)) //Si no se puede ni llamar ni ver mapa ni ver el enlace
            {
                //Se ocultan los botones de ubicación, teléfono y enlace
                holder.boton.setVisibility(View.GONE);
                holder.boton1.setVisibility(View.GONE);
                holder.boton2.setVisibility(View.GONE);
            }
            else if((elementos.get(position).getUbicacion1() != null) && (elementos.get(position).getUbicacion2() != null) && (elementos.get(position).getTelefono() == null) && (elementos.get(position).getEnlace() == null)) //Si solo se puede ver mapa
            {
                //Se ocultan los botones de teléfono y enlace
                holder.boton1.setVisibility(View.GONE);
                holder.boton2.setVisibility(View.GONE);

                //Se abre el mapa en la aplicación de Google Maps con la ubicación con un intent gracias a las dos variables del ArrayList Elementos que almacenan la ubicación
                holder.boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(Intent.ACTION_VIEW);
                        in.setData(Uri.parse(elementos.get(position).getUbicacion1() + Uri.encode(elementos.get(position).getUbicacion2())));
                        Intent chooser = Intent.createChooser(in, "Launch Maps");
                        v.getContext().startActivity(chooser);
                    }
                });
            }
            else if((elementos.get(position).getUbicacion1() == null) && (elementos.get(position).getUbicacion2() == null) && (elementos.get(position).getTelefono() != null) && (elementos.get(position).getEnlace() == null)) //Si solo se puede llamar
            {
                //Se ocultan los botones de ubicación y enlace
                holder.boton.setVisibility(View.GONE);
                holder.boton2.setVisibility(View.GONE);

                //Se abre la aplicación de teléfono con un intent con el número perteneciente al ArrayList ya marcado
                holder.boton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + elementos.get(position).getTelefono()));
                        v.getContext().startActivity(intent);
                    }
                });
            }
            else if((elementos.get(position).getUbicacion1() != null) && (elementos.get(position).getUbicacion2() != null) && (elementos.get(position).getTelefono() != null) && (elementos.get(position).getEnlace() == null)) //Si se puede llamar y ver mapa y pero no enlace
            {
                //Se oculta el boton de enlace
                holder.boton2.setVisibility(View.GONE);

                //Se abre el mapa en la aplicación de Google Maps con la ubicación con un intent gracias a las dos variables del ArrayList Elementos que almacenan la ubicación
                holder.boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(Intent.ACTION_VIEW);
                        in.setData(Uri.parse(elementos.get(position).getUbicacion1() + Uri.encode(elementos.get(position).getUbicacion2())));
                        Intent chooser = Intent.createChooser(in, "Launch Maps");
                        v.getContext().startActivity(chooser);
                    }
                });

                //Se abre la aplicación de teléfono con un intent con el número perteneciente al ArrayList ya marcado
                holder.boton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + elementos.get(position).getTelefono()));
                        v.getContext().startActivity(intent);
                    }
                });
            }
            else if((elementos.get(position).getUbicacion1() != null) && (elementos.get(position).getUbicacion2() != null) && (elementos.get(position).getTelefono() != null) && (elementos.get(position).getEnlace() != null)) //Si se puede llamar y ver mapa y ver enlace
            {
                //Se abre el mapa en la aplicación de Google Maps con la ubicación con un intent gracias a las dos variables del ArrayList Elementos que almacenan la ubicación
                holder.boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(Intent.ACTION_VIEW);
                        in.setData(Uri.parse(elementos.get(position).getUbicacion1() + Uri.encode(elementos.get(position).getUbicacion2())));
                        Intent chooser = Intent.createChooser(in, "Launch Maps");
                        v.getContext().startActivity(chooser);
                    }
                });

                //Se abre la aplicación de teléfono con un intent con el número perteneciente al ArrayList ya marcado
                holder.boton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + elementos.get(position).getTelefono()));
                        v.getContext().startActivity(intent);
                    }
                });

                //Se abre el enlace con un intent, usando el enlace del ArrayList Elementos
                holder.boton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri link = Uri.parse(elementos.get(position).getEnlace());
                        Intent intent = new Intent(Intent.ACTION_VIEW,link);
                        v.getContext().startActivity(intent);
                    }
                });
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
        if(listener != null)
        {
            listener.onClick(v);
        }
    }

    class MiViewHolder extends  RecyclerView.ViewHolder
    {
        TextView nombre, descripcion;
        ImageView imagen;
        Button boton, boton1, boton2;

        public MiViewHolder(@NonNull View itemView)
        {
            super(itemView);

            //Se asigna cada elemento de la interfaz
            nombre = (TextView) itemView.findViewById(R.id.tenombre);
            descripcion = (TextView) itemView.findViewById(R.id.tedescri);
            imagen = (ImageView) itemView.findViewById(R.id.imagen);
            boton = (Button) itemView.findViewById(R.id.botonubi);
            boton1 = (Button) itemView.findViewById(R.id.botonllamar);
            boton2 = (Button) itemView.findViewById(R.id.botonwiki);
        }
    }
}
