package es.styleapps.superrestaurant.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.io.Serializable;
import java.util.LinkedList;

import es.styleapps.superrestaurant.R;
import es.styleapps.superrestaurant.adapter.Plates_RecyclerViewAdapter;
import es.styleapps.superrestaurant.model.Plate;
import es.styleapps.superrestaurant.model.Table;
import es.styleapps.superrestaurant.model.Tables;

/**
 * Created by jlgarciaap on 10/12/16.
 */

public class Table_activity extends AppCompatActivity implements Plates_RecyclerViewAdapter.OnPlateClickListener, Serializable{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinkedList<Plate> mPlates;
    private Table mTable;
    private LinkedList<Table> mTables;
    private int mTablePressed;
    private int mPositionPressed;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_table_list);

        mTablePressed = (int) getIntent().getSerializableExtra("TABLEID");

        mTables = Tables.getTables();

        mTable = mTables.get(mTablePressed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(mTable.getTableNumber());
        //Le decimos a nuestra pantalla que esa es nuestra action bar
        setSupportActionBar(toolbar);

        if (mTable != null) {


            if(savedInstanceState != null){
                mPlates = (LinkedList<Plate>) savedInstanceState.getSerializable(mTable.getTableNumber());
            }
            if (mPlates == null) {
                mPlates = mTable.getPlates();
            }
        } else {

            mPlates.add(new Plate("Huevos FritosNULL", "Pos unos huevos con papas","Espero que no", R.drawable.ico_09, 40));
            mPlates.add(new Plate("Huevos FritosNULL2", "Pos unos huevos con papas","Espero que no", R.drawable.ico_09, 20));
            mPlates.add(new Plate("Huevos FritosNULL3", "Pos unos huevos con papas","Espero que no", R.drawable.ico_09, 30));
            mPlates.add(new Plate("Huevos FritosNULL4", "Pos unos huevos con papas","Espero que no", R.drawable.ico_09, 50));
            mPlates.add(new Plate("Huevos FritosNULL5", "Pos unos huevos con papas","Espero que no", R.drawable.ico_09, 60));
        }


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
            case 1: {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    Plate plateExample = (Plate) bundle.getSerializable("PRUEBA");
                    mPlates.add(plateExample);
                    //mTable.setPlate(plateExample);
                    //mTables.add(mTablePressed,mTable);
                    Tables.setTables(mTables);
                    mAdapter.notifyItemInserted(mPlates.size());
                    Tables_list_activity.adapterTable.notifyDataSetChanged();

                }
                if (resultCode == 2) {

                    Bundle bundle = data.getExtras();
                    Plate plateExample = (Plate) bundle.getSerializable("EXTRAS");
                    int position = mPlates.indexOf(plateExample);
                    mPlates.remove(mPositionPressed);
                    mPlates.add(plateExample);
                    Tables.setTables(mTables);
                    mAdapter.notifyItemInserted(mPlates.size());
                    Tables_list_activity.adapterTable.notifyDataSetChanged();
                    startActivity(getIntent());
                }
            }
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(mTable.getTableNumber(),mPlates);


    }


    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mPlates = (LinkedList<Plate>) savedInstanceState.getSerializable(mTable.getTableNumber());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onPlateClick(int position, Plate plate, View view) {

        //Pasamos al detail_plate_activity
        Intent resultIntent = new Intent(this,Detail_plate_activity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("PLATO",plate);
        resultIntent.putExtras(bundle);
        mPositionPressed = position;
        startActivityForResult(resultIntent,1, ActivityOptionsCompat.makeSceneTransitionAnimation(this,view,"transition").toBundle());


    }

    public AlertDialog total(){
        int totalCuenta = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        for(int i=0;i<mPlates.size();i++){

            totalCuenta += (int) mPlates.get(i).getPlatePrice();

        }

        builder.setTitle("TOTAL CUENTA").setMessage(String.valueOf(totalCuenta) + " €")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_buttons,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.total:
                AlertDialog total = total();
                total.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}