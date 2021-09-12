package com.example.huelvapedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.huelvapedia.ApiTiempo.Tiempo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
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
        setContentView(R.layout.activity_main);

        boton = findViewById(R.id.botontiempo); //Boton para la api del tiempo

        //Obtenemos la ActionBar
        ActionBar actionBar = getSupportActionBar();

        //Establecemos el icono en la ActionBar
        actionBar.setIcon(R.mipmap.ic_escudohuelva);
        actionBar.setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recicler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        array = new ArrayList<Elementos>(); //ArrayList que almacena todos los elmentos principales que componen cada tema

        reference = FirebaseDatabase.getInstance().getReference().child("principal"); //Esto coge la referencia llamada principal y todos sus elementos (temas principales)

        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) //Si puede leer datos de la BBDD
            {
                for(DataSnapshot dataSnapshot1: snapshot.getChildren())
                {
                    Elementos e =  dataSnapshot1.getValue(Elementos.class);
                    array.add(e); //Coge cada elemento de cada tema y lo almacena en una posición del array
                }
                adaptadorr = new Adaptador(MainActivity.this, array); //Adaptador del RecycleView que recibe como parámetro el array con todos los elementos del tema
                recyclerView.setAdapter(adaptadorr); //Establece el adaptador
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) //Si no puede leer datos de la BBDD
            {
                Toast.makeText(MainActivity.this, "Algo ha fallado", Toast.LENGTH_SHORT).show();
            }
        });

        boton.setOnClickListener(new View.OnClickListener() { //Para hacer click en el botón de la api, se abrirá la Activity Tiempo, que muestra el tiempo en la ciudad
            @Override
            public void onClick(View v) {
                Intent in = new Intent(v.getContext(), Tiempo.class);
                startActivity(in);
            }
        });
    }
}