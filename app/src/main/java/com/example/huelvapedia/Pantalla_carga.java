package com.example.huelvapedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Pantalla_carga extends AppCompatActivity {

    private static int PANTALLA_CARGA = 5000;

    //Variables
    Animation topAnim, bottomAnim;
    ImageView imagenlogo;
    TextView textocarga, slogancarga;
    ProgressBar progressBar;

    //Para comprobar si tenemos conexión a internet
    boolean hayConexion = true;

    //Para comprobar si estamos monitorizando la conexion
    private boolean Monitorizando = false;

    private ConnectivityManager.NetworkCallback connectivityCallback = new ConnectivityManager.NetworkCallback() //Llamada de nuevo a la conexión
    {
        @Override
        public void onAvailable(Network network) //Si la red esta disponible
        {
            hayConexion = true;

            //Inicia las animaciones con su duración y carga la Pantalla principal
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Pantalla_carga.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, PANTALLA_CARGA);
        }

        @Override
        public void onLost(Network network) //Si la red no esta disponible
        {
            hayConexion = false;
            Toast.makeText(Pantalla_carga.this, "SE DEBE DISPONER DE CONEXIÓN A INTERNET", Toast.LENGTH_LONG).show();
        }
    };

    private void checkConnectivity() //Método que comprueba la conectividad
    {
        final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE); //Cogemos el servicio de verificación conexión

        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo(); //Cogemos la info de la conexión

        hayConexion = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting(); //Si hay conexion o se esta conectando

        if(hayConexion)
        {
            //Inicia las animaciones con su duración y carga la Pantalla principal
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Pantalla_carga.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, PANTALLA_CARGA);
        }

        if (!hayConexion) //Si no hay conexión mostramos un TOAST
        {
            Toast.makeText(Pantalla_carga.this, "SE DEBE DISPONER DE CONEXIÓN A INTERNET", Toast.LENGTH_LONG).show();
            // if Network is not connected we will register a network callback to  monitor network
            connectivityManager.registerNetworkCallback(
                    new NetworkRequest.Builder()
                            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                            .build(), connectivityCallback);
            Monitorizando = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN); //Poner el layout a pantalla completa
        setContentView(R.layout.activity_pantalla_carga);
        getSupportActionBar().hide(); //Oculta la barra superior

        //Animaciones
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imagenlogo = findViewById(R.id.imagenlogo);
        textocarga = findViewById(R.id.textocarga);
        slogancarga = findViewById(R.id.slogancarga);
        progressBar = findViewById(R.id.progressBar);

        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN); //Establece el color del circulo de carga en blanco

        //Establecemos las animaciones para cada elemento del layout
        imagenlogo.setAnimation(topAnim);
        textocarga.setAnimation(bottomAnim);
        slogancarga.setAnimation(bottomAnim);
        progressBar.setAnimation(bottomAnim);

        super.onResume();
        checkConnectivity(); //Iniciamos el método de conexión
    }

    protected void onPause() {
        //Si está monitorizando y termina la activity, teerminamos la monitorización
        if (Monitorizando) {
            final ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.unregisterNetworkCallback(connectivityCallback);
            Monitorizando = false;
        }
        super.onPause();
    }
}