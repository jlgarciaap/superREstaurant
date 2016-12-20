package es.styleapps.superrestaurant.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import es.styleapps.superrestaurant.R;
import es.styleapps.superrestaurant.adapter.Plates_RecyclerViewAdapter;
import es.styleapps.superrestaurant.model.Plate;

/**
 * Created by jlgarciaap on 11/12/16.
 */

public class Plates_activity extends AppCompatActivity implements Plates_RecyclerViewAdapter.OnPlateClickListener, Serializable {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinkedList<Plate> mPlates = new LinkedList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_plate_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Platos Disponibles");
        setSupportActionBar(toolbar);
        String result = null;

        MyAsyncTask async = new MyAsyncTask();
         async.execute();
        try {
            result = async.get().toString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        mPlates = parserJSON(result);

//        mPlates.add(new Plate("Spaghettis", "Pos unos huevos con papas","Espero que no", R.drawable.emperador, 40));
//        mPlates.add(new Plate("Spaghettis2", "Pos unos huevos con papas","Espero que no", R.drawable.solternera, 20));
//        mPlates.add(new Plate("Spaghettis2", "Pos unos huevos con papas","Espero que no", R.drawable.solternera, 20));





        mAdapter = new Plates_RecyclerViewAdapter(mPlates,this);

        mRecyclerView = (RecyclerView) findViewById(R.id.reciclador);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private LinkedList<Plate> parserJSON (String s){
        //Analizamos losdatos para convertirlos de JSON a algo manejable
        JSONObject jsonRoot = null;
        JSONArray plates = null;
        LinkedList<Plate> platesList = new LinkedList<>();
        try {
           jsonRoot = new JSONObject(s);
           plates = new JSONArray(jsonRoot);

        //Añadimos una lista para ir almacenando los forecast


        for (int i = 0; i < plates.length(); i++) { //Añadido para recorrer todos los dias
            JSONObject plate = null;

            plate = plates.getJSONObject(i);

            String name = plate.getString("name");
            String description = plate.getString("description");
            String alergy = plate.getString("alergens");
            int image = plate.getInt("image");
            float price = plate.getInt("price");

            platesList.add(new Plate(name, description, alergy, image, price));
        }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            //Creamos el objeto forecast
            //return new Forecast(max, min, humidity, description, iconResource);

        return platesList;
        }




    @Override
    public void onPlateClick(int position, Plate plate, View view) {
        Intent resultIntent = new Intent();

        Bundle bundle = new Bundle();
        bundle.putSerializable("PRUEBA", plate);
        resultIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();

    }

//    private void downloadForecast() {
//
//        //No podemos bloquear asi que tiramos de asincronia
//        //Es parametro de entrada, como medimos la descarga y lo que devuelve
//        //Lo mismo que antes ahora devolvemos una lista de Forecast ya que queremos ma de un dia
//        //AsyncTask<City, Integer, Forecast> weatherDownloader = new AsyncTask<City, Integer, Forecast>() {
//        AsyncTask<City, Integer, LinkedList<Forecast>> weatherDownloader = new AsyncTask<City, Integer, LinkedList<Forecast>>() {
//            //El INTEGER es la forma en que medimos la descarga, normalmente integer pero si fueran objetos pondriamos otra cosa
//            @Override
//            protected void onPreExecute() {
//                //Aqui es donde tocamos temas de vistas con el viewSwitcher
//                super.onPreExecute();
//
//                mVSwitcher.setDisplayedChild(LOADING_VIEW_INDEX);
//            }
//
//            @Override
//            // protected Forecast doInBackground(City... cities) {//Los 3 puntos indica lista indefenida de parametros
//            protected LinkedList<Forecast> doInBackground(City... cities) {
//                //Esto se ejecuta en otro hilo que no es el principal
//                URL url = null;
//                InputStream input = null;
//
//                try {
//                    //Nos bajamos los datos
//                    url = new URL(String.format("http://www.mocky.io/v2/5856f0b61300008317fddccb",
//                            cities[0].getName()));//Solo queremos el primero
//
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    connection.connect();
//
//                    int responseLenght = connection.getContentLength();
//                    byte data [] = new byte[1024];
//                    long currentBytes = 0;
//                    int downloadedBytes;
//                    input = connection.getInputStream();
//                    StringBuilder sb = new StringBuilder();
//                    while ((downloadedBytes = input.read(data)) != -1){
//
//                        sb.append(new String(data, 0, downloadedBytes));//Aqui vamos acumulando en el caso de que recibamos poco a poco
//
//                        //Para mostrar un poco el progeso lo podemos hacer aqui
//                        publishProgress((int)(currentBytes * 100)/ responseLenght);
//                    }
//
//                    //Analizamos losdatos para convertirlos de JSON a algo manejable
//                    JSONObject jsonRoot = new JSONObject(sb.toString());
//                    JSONArray days = jsonRoot.getJSONArray("list");
//
//                    //Añadimos una lista para ir almacenando los forecast
//                    LinkedList<Forecast> forecasts = new LinkedList<>();
//
//                    for(int i = 0; i < days.length(); i++) { //Añadido para recorrer todos los dias
//                        JSONObject today = days.getJSONObject(i);
//                        float max = (float) today.getJSONObject("temp").getDouble("max");
//                        float min = (float) today.getJSONObject("temp").getDouble("min");
//                        float humidity = (float) today.getDouble("humidity");
//                        String description = today.getJSONArray("weather").getJSONObject(0).getString("description");
//                        String iconString = today.getJSONArray("weather").getJSONObject(0).getString("icon");
//
//                        //Convertimos el texto icono a drawable
//                        iconString = iconString.substring(0, iconString.length() - 1);
//                        int iconInt = Integer.parseInt(iconString);
//                        int iconResource = R.drawable.ico_01;
//                        switch (iconInt) {
//                            case 1:
//                                iconResource = R.drawable.ico_01;
//                                break;
//                            case 2:
//                                iconResource = R.drawable.ico_02;
//                                break;
//                            case 3:
//                                iconResource = R.drawable.ico_03;
//                                break;
//                            case 13:
//                                iconResource = R.drawable.ico_13;
//                                break;
//                            case 9:
//                                iconResource = R.drawable.ico_09;
//                                break;
//                            case 10:
//                                iconResource = R.drawable.ico_10;
//                                break;
//                            case 11:
//                                iconResource = R.drawable.ico_11;
//                                break;
//                            default:
//                                iconResource = R.drawable.ico_01;
//                                break;
//                        }
//
//
//
//
//                        //Creamos el objeto forecast
//                        //return new Forecast(max, min, humidity, description, iconResource);
//
//                        forecasts.add(new Forecast(max, min, humidity, description, iconResource));
//
//                    }
//
//                    Thread.sleep(3000);
//                    //Simulamos artificialmente la descarga
//
//                    //Devolvemos la lista
//                    return forecasts;
//
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                }
//
//                return null;
//
//            }
//
//            @Override
//            //protected void onPostExecute(Forecast forecast) {
//            protected void onPostExecute(LinkedList<Forecast> forecast) {
//
//                //Aqui actualizariamos la vista cuando termine
//                super.onPostExecute(forecast);
//
//                if(forecast != null){
//                    mCity.setForecast(forecast);
//
//                    //Actualizamos interfaz
//                    setForecast(forecast);// Una vez que recibimos varios dias tenemos que modificar setForecast para que reciba una lista
//
//                } else {
//                    //Error, se lo notificamos al usuario
//                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
//                    alertDialog.setTitle("Error");
//                    alertDialog.setMessage("Error en la descarga");
//                    alertDialog.setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            downloadForecast();
//                        }
//                    });
//                    alertDialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//
//                        }
//                    });
//                    //Con esto mostramos el dialogo
//                    alertDialog.show();
//
//                }
//
//
//            }
//
//            @Override
//            protected void onProgressUpdate(Integer... values) {
//
//                super.onProgressUpdate(values);
//            }
//
//
//        };
//
//        weatherDownloader.execute(mCity); //Seria el City de la AsyncTask
//
//
//    }

}


class MyAsyncTask extends AsyncTask<String,Integer, String> {

    URL url = null;
    InputStream input = null;
    StringBuilder sb = null;

    @Override
    protected String doInBackground(String... params) {

        try {
            //Nos bajamos los datos
            url = new URL("http://www.mocky.io/v2/5856f0b61300008317fddccb");//Solo queremos el primero

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            int responseLenght = connection.getContentLength();
            byte data[] = new byte[1024];
            long currentBytes = 0;
            int downloadedBytes;
            input = connection.getInputStream();
            sb = new StringBuilder();
            while ((downloadedBytes = input.read(data)) != -1) {

                sb.append(new String(data, 0, downloadedBytes));//Aqui vamos acumulando en el caso de que recibamos poco a poco

                //Para mostrar un poco el progeso lo podemos hacer aqui
                publishProgress((int) (currentBytes * 100) / responseLenght);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Simulamos artificialmente la descarga

        //Devolvemos la lista
        return sb.toString();

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //Analizamos losdatos para convertirlos de JSON a algo manejable
        JSONObject jsonRoot = null;
        JSONArray plates = null;
        try {
            jsonRoot = new JSONObject(s);
            plates = new JSONArray(jsonRoot);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Añadimos una lista para ir almacenando los forecast
        LinkedList<Plate> platesList = new LinkedList<>();

        for (int i = 0; i < plates.length(); i++) { //Añadido para recorrer todos los dias
            JSONObject plate = null;
            try {
                plate = plates.getJSONObject(i);

                String name = plate.getString("name");
                String description = plate.getString("description");
                String alergy = plate.getString("alergens");
                int image = plate.getInt("image");
                float price = plate.getInt("price");

                platesList.add(new Plate(name, description, alergy, image, price));

            } catch (JSONException e) {
                e.printStackTrace();
            }


            //Creamos el objeto forecast
            //return new Forecast(max, min, humidity, description, iconResource);


        }
    }

}