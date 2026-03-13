package com.example.huelvapedia.ApiTiempo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.huelvapedia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class Tiempo extends AppCompatActivity {

    // TODO: Mover la API key a local.properties y acceder via BuildConfig para evitar exposición
    private static final String URL_BASE = "http://api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY = "c0d138083fb0634d6d5cd36adafc9a77";

    private TextView tvTemperatura, tvDescripcion, tvSensacion, tvPresion, tvHumedad, tvNubes, tvViento;
    private ImageView imagenDelTiempo;

    private final DecimalFormat formatoDecimal = new DecimalFormat("#.#");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiempo);

        tvTemperatura = findViewById(R.id.temperatura);
        tvDescripcion = findViewById(R.id.descripcion);
        tvSensacion = findViewById(R.id.sensacion);
        tvPresion = findViewById(R.id.presion);
        tvHumedad = findViewById(R.id.humedad);
        tvNubes = findViewById(R.id.nubes);
        tvViento = findViewById(R.id.viento);

        imagenDelTiempo = findViewById(R.id.imagentiempo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_escudohuelva);
        actionBar.setDisplayShowHomeEnabled(true);

        String urlCompleta = URL_BASE + "?q=Huelva,ES&appid=" + API_KEY;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlCompleta,
                response -> {
                    try
                    {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        String descripcion = jsonObjectWeather.getString("description");
                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        double sensacion = jsonObjectMain.getDouble("feels_like") - 273.15;
                        float presion = jsonObjectMain.getInt("pressure");
                        int humedad = jsonObjectMain.getInt("humidity");
                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                        String viento = jsonObjectWind.getString("speed");
                        JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                        String nubes = jsonObjectClouds.getString("all");

                        traducirDescripcion(descripcion);

                        tvTemperatura.setText(formatoDecimal.format(temp) + "ºC");
                        tvSensacion.setText("Sensacion térmica: " + formatoDecimal.format(sensacion) + "ºC");
                        tvHumedad.setText("Humedad: " + humedad + "%");
                        tvNubes.setText("Cantidad de nubes: " + nubes + "%");
                        tvViento.setText("Velocidad del viento: " + viento + "m/s (metros por segundo)");
                        tvPresion.setText("Presión atmosférica: " + presion + " hPa");
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show()
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    /**
     * Traduce la descripción del tiempo de inglés a español y establece el icono correspondiente.
     */
    private void traducirDescripcion(String descripcion)
    {
        switch (descripcion)
        {
            case "clear sky":
                tvDescripcion.setText("Despejado");
                imagenDelTiempo.setImageResource(R.mipmap.ic_soleado);
                break;
            case "few clouds":
                tvDescripcion.setText("Ligeramente nublado");
                imagenDelTiempo.setImageResource(R.mipmap.ic_ligeramentenublado);
                break;
            case "scattered clouds":
                tvDescripcion.setText("Nubes dispersas");
                imagenDelTiempo.setImageResource(R.mipmap.ic_nubesdispersas);
                break;
            case "overcast clouds":
                tvDescripcion.setText("Nublado");
                imagenDelTiempo.setImageResource(R.mipmap.ic_nublado);
                break;
            case "light rain":
                tvDescripcion.setText("Lluvia ligera");
                imagenDelTiempo.setImageResource(R.mipmap.ic_lluvialigera);
                break;
            case "broken clouds":
                tvDescripcion.setText("Nubes rotas");
                imagenDelTiempo.setImageResource(R.mipmap.ic_nubesrotas);
                break;
            case "heavy intensity rain":
                tvDescripcion.setText("Lluvia pesada");
                imagenDelTiempo.setImageResource(R.mipmap.ic_lluviafuerte);
                break;
            case "moderate rain":
                tvDescripcion.setText("Lluvia moderada");
                imagenDelTiempo.setImageResource(R.mipmap.ic_lluviamoderada);
                break;
        }
    }
}