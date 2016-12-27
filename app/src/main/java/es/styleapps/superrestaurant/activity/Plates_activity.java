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

        //Llamamos a la tarea asincrona para que realice la descarga. No le he puesto lo del tiempo
        //Para simular bloqueo. Solo seria añadir lo que vimos en el curso
        MyAsyncTask async = new MyAsyncTask();
         async.execute();
        try {
            result = async.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        //Mandamos el resultado a parsear
        mPlates = parserJSON(result);



        mAdapter = new Plates_RecyclerViewAdapter(mPlates,getBaseContext(),this);

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
            plates = jsonRoot.getJSONArray("plates");

        for (int i = 0; i < plates.length(); i++) { //Añadido para recorrer todos los dias
            JSONObject plate = null;

            plate = plates.getJSONObject(i);

            String name = plate.getString("name");
            String description = plate.getString("description");
            String alergy = plate.getString("alergy");
            int image = plate.getInt("image");
            float price = plate.getInt("price");

            platesList.add(new Plate(name, description, alergy, image, price));

        }
            } catch (Exception ex){

             ex.printStackTrace();
        }

        return platesList;
        }




    @Override
    public void onPlateClick(int position, Plate plate, View view) {
        Intent resultIntent = new Intent();
        //Pasamos el plato seleccionado al fragment table
        Bundle bundle = new Bundle();
        bundle.putSerializable(getString(R.string.EXTRA_PLATE), plate);
        resultIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();

    }


}


class MyAsyncTask extends AsyncTask<String,Integer, String> {

    private URL url = null;
    private InputStream input = null;
    private StringBuilder sb = null;

    @Override
    protected String doInBackground(String... params) {

        try {
            //Nos bajamos los datos
            url = new URL("http://www.mocky.io/v2/585c35e01200001f185642a6");//Solo queremos el primero

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


        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();

    }


}

