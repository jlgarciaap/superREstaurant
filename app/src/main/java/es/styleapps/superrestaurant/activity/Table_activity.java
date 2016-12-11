package es.styleapps.superrestaurant.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.FragmentManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;

import es.styleapps.superrestaurant.R;
import es.styleapps.superrestaurant.adapter.Plates_RecyclerViewAdapter;
import es.styleapps.superrestaurant.fragment.Table_fragment;
import es.styleapps.superrestaurant.model.Plate;
import es.styleapps.superrestaurant.model.Table;

/**
 * Created by jlgarciaap on 10/12/16.
 */

public class Table_activity extends AppCompatActivity implements Plates_RecyclerViewAdapter.OnPlateClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinkedList<Plate> mPlates;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Le decimos a nuestra pantalla que esa es nuestra action bar
        setSupportActionBar(toolbar);

        setContentView(R.layout.table_activity);


//        //Cargamos a mano el fragment manager
//        FragmentManager fm = getFragmentManager();
//
//        if (fm.findFragmentById(R.id.table_fragment) == null) {
//
//            fm.beginTransaction().add(R.id.table_fragment, new Table_fragment()).commit();
//
//        }

        //Este seria el de la tabla, lo suyo seria con un putExtra que recibiera la lista de la mesa

       mPlates = new LinkedList<>();

        mPlates.add(new Plate("Huevos Fritos", "Pos unos huevos con papas","Espero que no", R.drawable.ico_09, 40));
        mPlates.add(new Plate("Huevos Fritos2", "Pos unos huevos con papas","Espero que no", R.drawable.ico_09, 20));
        mPlates.add(new Plate("Huevos Fritos3", "Pos unos huevos con papas","Espero que no", R.drawable.ico_09, 30));
        mPlates.add(new Plate("Huevos Fritos4", "Pos unos huevos con papas","Espero que no", R.drawable.ico_09, 50));
        mPlates.add(new Plate("Huevos Fritos5", "Pos unos huevos con papas","Espero que no", R.drawable.ico_09, 60));

        setContentView(R.layout.fragment_table_list);

        mAdapter = new Plates_RecyclerViewAdapter(mPlates,this);

        mRecyclerView = (RecyclerView) findViewById(R.id.reciclador);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Table_activity.this, Plates_activity.class);
                startActivityForResult(intent, 1);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 1:{
                if(resultCode == Activity.RESULT_OK){

                    Intent intent = this.getIntent();
                    Bundle bundle = data.getExtras();

                    Plate plateExample = (Plate) bundle.getSerializable("PRUEBA");
                    String platename = plateExample.getPlateName();
                    String platePrice = String.valueOf(plateExample.getPlatePrice());
                    String prueba = "Hemmos llegad";
                    mPlates.add(plateExample);
                    mAdapter.notifyItemInserted(mPlates.size());


                }
            }
        }

    }


    @Override
    public void onPlateClick(int position, Plate plate, View view) {

    }
}