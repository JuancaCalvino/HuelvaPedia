package com.example.huelvapedia.ApiTiempo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.huelvapedia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class Tiempo extends AppCompatActivity {

    private final String url = "http://api.openweathermap.org/data/2.5/weather"; //Url de la que se obtendran los datos
    private final String api = "c0d138083fb0634d6d5cd36adafc9a77"; //Clave de la API

    TextView tvtemperatura, tvdescripcion, tvsensacion, tvpresion, tvhumedad, tvnubes, tvviento;
    ImageView imagendeltiempo;

    DecimalFormat df = new DecimalFormat("#.#"); //Formato que se le dará a las temperaturas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiempo);

        tvtemperatura = findViewById(R.id.temperatura);
        tvdescripcion = findViewById(R.id.descripcion);
        tvsensacion = findViewById(R.id.sensacion);
        tvpresion = findViewById(R.id.presion);
        tvhumedad = findViewById(R.id.humedad);
        tvnubes = findViewById(R.id.nubes);
        tvviento = findViewById(R.id.viento);

        imagendeltiempo = findViewById(R.id.imagentiempo);

        //Obtenemos la ActionBar
        ActionBar actionBar = getSupportActionBar();

        //Establecemos el icono en la ActionBar
        actionBar.setIcon(R.mipmap.ic_escudohuelva);
        actionBar.setDisplayShowHomeEnabled(true);

        String tempUrl = url + "?q=" + "Huelva" + "," + "ES" + "&appid=" + api; //En la API los parámetros son la url, la ciudad, el código de país y la clave de la API

        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() //Realizamos la solicitud por metodo POST
        {
            @Override
            public void onResponse(String response)
            {
                //Obtenemos y guardamos cada dato recibido en una variable
                try
                {
                    JSONObject jsonResponse = new JSONObject(response);                         //Objeto que contiene la respuesta de la solicitud, en formato JSON
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");           //Array de los objetos de la respuesta del JSON, obtenemos el array perteneciente a tiempo
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);            //Obtenemos los objetos del array del tiempo, la posición 0 del array
                    String descripcion = jsonObjectWeather.getString("description");      //De los objetos obtenemos la descripción
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");             //Obtenenemos el objeto main que alamacena la temeperatura, sensación, presion y humedad
                    double temp = jsonObjectMain.getDouble("temp") - 273.15;              //Obtenemos el primer double, perteneciente a la temperatura
                    double sensacion = jsonObjectMain.getDouble("feels_like") - 273.15;   //Obtenemos la sensacion en kelvin, y lo pasamos a grados Celsius
                    float presion = jsonObjectMain.getInt("pressure");                    //Obtenemos la presion
                    int humedad = jsonObjectMain.getInt("humidity");                      //Obtenemos la humedad
                    JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");             //Obtenemos el objeto viento que almacena la velocidad del mismo
                    String viento = jsonObjectWind.getString("speed");                    //Obtenemos la velocidad del viento
                    JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");         //Obtenemos el objeto nubes que almacena el porcentaje de nubosidad
                    String nubes = jsonObjectClouds.getString("all");                     //Obtenemos el porcentaje de nubosidad del objeto nubes

                    //Como la descripción viene en inglés, se traducen a español (Solo se han traducido las mayormente posibles, por ejemplo, la nevada no se encuentra)
                    switch(descripcion)
                    {
                        case "clear sky":
                        {
                            tvdescripcion.setText("Despejado");
                            imagendeltiempo.setImageResource(R.mipmap.ic_soleado);
                            break;
                        }
                        case "few clouds":
                        {
                            tvdescripcion.setText("Ligeramente nublado");
                            imagendeltiempo.setImageResource(R.mipmap.ic_ligeramentenublado);
                            break;
                        }
                        case "scattered clouds":
                        {
                            tvdescripcion.setText("Nubes dispersas");
                            imagendeltiempo.setImageResource(R.mipmap.ic_nubesdispersas);
                            break;
                        }
                        case "overcast clouds":
                        {
                            tvdescripcion.setText("Nublado");
                            imagendeltiempo.setImageResource(R.mipmap.ic_nublado);
                            break;
                        }
                        case "light rain":
                        {
                            tvdescripcion.setText("Lluvia ligera");
                            imagendeltiempo.setImageResource(R.mipmap.ic_lluvialigera);
                            break;
                        }
                        case "broken clouds":
                        {
                            tvdescripcion.setText("Nubes rotas");
                            imagendeltiempo.setImageResource(R.mipmap.ic_nubesrotas);
                            break;
                        }
                        case "heavy intensity rain":
                        {
                            tvdescripcion.setText("Lluvia pesada");
                            imagendeltiempo.setImageResource(R.mipmap.ic_lluviafuerte);
                            break;
                        }
                        case "moderate rain":
                        {
                            tvdescripcion.setText("Lluvia moderada");
                            imagendeltiempo.setImageResource(R.mipmap.ic_lluviamoderada);
                            break;
                        }
                    }

                    tvtemperatura.setText(df.format(temp) + "ºC");

                    tvsensacion.setText("Sensacion térmica: " + df.format(sensacion) + "ºC");
                    tvhumedad.setText("Humedad: " + humedad + "%");
                    tvnubes.setText("Cantidad de nubes: " + nubes + "%");
                    tvviento.setText("Velocidad del viento: " + viento + "m/s (metros por segundo)");
                    tvpresion.setText("Presión atmosférica: " + presion + " hPa");

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) //Si se produce un error, se muestra en un Toast
            {
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });

        //Se crea una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}