package com.example.huelvapedia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.huelvapedia.ApiTiempo.Tiempo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Secundaria extends AppCompatActivity
{
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Elementos> array;
    Adaptador adaptadorr;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Uso el mismo layout para los subtemas

        boton = findViewById(R.id.botontiempo);
        boton.setVisibility(View.GONE); //Oculto el botón del tiempo en las ventanas de los subtemas

        //Obtenemos la ActionBar instalada por AppCompatActivity
        ActionBar actionBar = getSupportActionBar();

        //Establecemos el icono en la ActionBar
        actionBar.setIcon(R.mipmap.ic_escudohuelva);
        actionBar.setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recicler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        array = new ArrayList<Elementos>();

        //Aqui recibo los datos (el nombre) para poder coger la referencia del subtema de la bd
        Intent intent = getIntent();
        String pnombre = intent.getStringExtra("Nombre"); //Recibo el nombre del tema anterior por un intent y la guardo en una variable

        if(pnombre.contains(" ")) //Si el nombre contiene espacios se quitan y se convierte a minuscula
        {
            pnombre = pnombre.replace(" ","");
            pnombre = pnombre.toLowerCase();
        }
        else
            pnombre = pnombre.toLowerCase(); //Se convierte a minuscula

        reference = FirebaseDatabase.getInstance().getReference().child(pnombre); //Coge la referencia del nombre del tema anterio ya convertidoa minúscula y sin espacios

        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) //Si puede leer datos de la BBDD
            {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren())
                {
                    Elementos e = dataSnapshot1.getValue(Elementos.class);
                    array.add(e); //Coge cada elemento de cada tema y lo almacena en una posición del array
                }

                adaptadorr = new Adaptador(Secundaria.this, array); //Adaptador del RecycleView que recibe como parámetro el array con todos los elementos del tema
                recyclerView.setAdapter(adaptadorr); //Establece el adaptador
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) //Si no puede leer datos de la BBDD
            {
                Toast.makeText(Secundaria.this, "Algo ha fallado", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
