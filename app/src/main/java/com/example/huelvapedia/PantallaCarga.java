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
import android.os.Looper;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class PantallaCarga extends AppCompatActivity {

    private static final int DURACION_CARGA = 5000;

    private Animation animacionSuperior, animacionInferior;
    private ImageView imagenLogo;
    private TextView textoCarga, sloganCarga;
    private ProgressBar progressBar;

    private boolean hayConexion = true;
    private boolean monitorizando = false;

    private final Handler handler = new Handler(Looper.getMainLooper());

    private final Runnable navegarAPrincipal = () -> {
        Intent intent = new Intent(PantallaCarga.this, MainActivity.class);
        startActivity(intent);
        finish();
    };

    private final ConnectivityManager.NetworkCallback connectivityCallback = new ConnectivityManager.NetworkCallback()
    {
        @Override
        public void onAvailable(Network network)
        {
            hayConexion = true;
            handler.postDelayed(navegarAPrincipal, DURACION_CARGA);
        }

        @Override
        public void onLost(Network network)
        {
            hayConexion = false;
            Toast.makeText(PantallaCarga.this, "SE DEBE DISPONER DE CONEXIÓN A INTERNET", Toast.LENGTH_LONG).show();
        }
    };

    private void checkConnectivity()
    {
        final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        hayConexion = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();

        if (hayConexion)
        {
            handler.postDelayed(navegarAPrincipal, DURACION_CARGA);
        }
        else
        {
            Toast.makeText(PantallaCarga.this, "SE DEBE DISPONER DE CONEXIÓN A INTERNET", Toast.LENGTH_LONG).show();
            connectivityManager.registerNetworkCallback(
                    new NetworkRequest.Builder()
                            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                            .build(), connectivityCallback);
            monitorizando = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_carga);
        getSupportActionBar().hide();

        animacionSuperior = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        animacionInferior = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        imagenLogo = findViewById(R.id.imagenlogo);
        textoCarga = findViewById(R.id.textocarga);
        sloganCarga = findViewById(R.id.slogancarga);
        progressBar = findViewById(R.id.progressBar);

        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        imagenLogo.setAnimation(animacionSuperior);
        textoCarga.setAnimation(animacionInferior);
        sloganCarga.setAnimation(animacionInferior);
        progressBar.setAnimation(animacionInferior);

        checkConnectivity();
    }

    @Override
    protected void onPause() {
        if (monitorizando) {
            final ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.unregisterNetworkCallback(connectivityCallback);
            monitorizando = false;
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null); // Cancela callbacks pendientes para evitar memory leaks
        super.onDestroy();
    }
}
