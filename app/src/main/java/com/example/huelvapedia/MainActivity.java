package com.example.huelvapedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private ArrayList<Elementos> listaElementos;
    private Adaptador adaptador;
    private Button botonTiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonTiempo = findViewById(R.id.botontiempo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_escudohuelva);
        actionBar.setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recicler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaElementos = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("principal");

        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                listaElementos.clear(); // Evita duplicación de datos cuando Firebase notifica cambios
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Elementos elemento = dataSnapshot.getValue(Elementos.class);
                    listaElementos.add(elemento);
                }
                adaptador = new Adaptador(MainActivity.this, listaElementos);
                recyclerView.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(MainActivity.this, "Algo ha fallado", Toast.LENGTH_SHORT).show();
            }
        });

        botonTiempo.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), Tiempo.class);
            startActivity(intent);
        });
    }
}