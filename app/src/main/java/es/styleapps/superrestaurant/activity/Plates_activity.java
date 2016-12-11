package es.styleapps.superrestaurant.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_plate_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Platos Disponibles");
        setSupportActionBar(toolbar);

        LinkedList<Plate> plates = new LinkedList<>();

        plates.add(new Plate("Spaghettis", "Pos unos huevos con papas","Espero que no", R.drawable.ico_13, 40));
        plates.add(new Plate("Spaghettis2", "Pos unos huevos con papas","Espero que no", R.drawable.ico_13, 20));
        plates.add(new Plate("Spaghettis3", "Pos unos huevos con papas","Espero que no", R.drawable.ico_13, 30));
        plates.add(new Plate("Spaghettis4", "Pos unos huevos con papas","Espero que no", R.drawable.ico_13, 50));
        plates.add(new Plate("Spaghettis5", "Pos unos huevos con papas","Espero que no", R.drawable.ico_13, 60));



        mAdapter = new Plates_RecyclerViewAdapter(plates,this);

        mRecyclerView = (RecyclerView) findViewById(R.id.reciclador);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


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
}
