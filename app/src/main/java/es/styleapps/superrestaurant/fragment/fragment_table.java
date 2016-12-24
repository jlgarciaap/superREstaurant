package es.styleapps.superrestaurant.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import es.styleapps.superrestaurant.R;
import es.styleapps.superrestaurant.activity.Detail_plate_activity;
//import es.styleapps.superrestaurant.activity.Table_activity;
import es.styleapps.superrestaurant.activity.Plates_activity;
import es.styleapps.superrestaurant.adapter.Plates_RecyclerViewAdapter;
import es.styleapps.superrestaurant.model.Plate;
import es.styleapps.superrestaurant.model.Table;
import es.styleapps.superrestaurant.model.Tables;

/**
 * Created by jlgarciaap on 23/12/16.
 */

public class fragment_table extends Fragment implements Plates_RecyclerViewAdapter.OnPlateClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinkedList<Plate> mPlates;
    private Table mTable;
    private LinkedList<Table> mTables;
    private int mTablePressed;
    private int mPositionPressed;


    public static fragment_table newInstance(){
//        Bundle arguments = new Bundle();
//        arguments.putInt(ARG_CITY_INDEX, cityIndex);

        fragment_table table = new fragment_table();
//        cityPagerFragment.setArguments(arguments);

        return table;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);



        View root = inflater.inflate(R.layout.fragment_table_list, container, false);


        mTables = Tables.getTables();


        if (getActivity().getIntent().getSerializableExtra("TABLEID") != null){

            mTablePressed = (int) getActivity().getIntent().getSerializableExtra("TABLEID");
            mTable = mTables.get(mTablePressed);
        } else {

            mTable = mTables.get(0);

        }

        //Le decimos a nuestra pantalla que esa es nuestra action bar

        if (mTable != null) {

            if(savedInstanceState != null){
                mPlates = (LinkedList<Plate>) savedInstanceState.getSerializable(mTable.getTableNumber());
            }
            if (mPlates == null) {
                mPlates = mTable.getPlates();
            }
        } else {

            mPlates.add(new Plate("Huevos FritosNULL", "Pos unos huevos con papas","Espero que no", R.drawable.spaghetti, 40));
            mPlates.add(new Plate("Huevos FritosNULL2", "Pos unos huevos con papas","Espero que no", R.drawable.solternera, 20));
            mPlates.add(new Plate("Huevos FritosNULL3", "Pos unos huevos con papas","Espero que no", R.drawable.huevoschorizo, 30));
            mPlates.add(new Plate("Huevos FritosNULL4", "Pos unos huevos con papas","Espero que no", R.drawable.emperador, 50));

        }


        mAdapter = new Plates_RecyclerViewAdapter(mPlates, getActivity().getBaseContext(), this);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.reciclador);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        FloatingActionButton addButton = (FloatingActionButton) root.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Plates_activity.class);
                startActivityForResult(intent, 1);
            }
        });


        return root;

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 1: {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    Plate plateExample = (Plate) bundle.getSerializable("PRUEBA");
                    mPlates.add(plateExample);
                    Tables.setTables(mTables);
                    mAdapter.notifyItemInserted(mPlates.size());
                    // Tables_list_activity.adapterTable.notifyDataSetChanged();
                    fragment_tables_list.adapterTable.notifyDataSetChanged();


                }
                if (resultCode == 2) {

                    Bundle bundle = data.getExtras();
                    Plate plateExample = (Plate) bundle.getSerializable("EXTRAS");
                    mPlates.set(mPositionPressed, plateExample);
                    mAdapter.notifyItemChanged(mPositionPressed);
                    //Tables_list_activity.adapterTable.notifyDataSetChanged();
                    fragment_tables_list.adapterTable.notifyDataSetChanged();
                    onResume();
                }
            }
        }

    }


    @Override
    public void onResume() {
        mTables = Tables.getTables();
        super.onResume();

    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(mTable.getTableNumber(),mPlates);


    }










    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onPlateClick(int position, Plate plate, View view) {

        //Pasamos al detail_plate_activity
        Intent resultIntent = new Intent(getActivity(),Detail_plate_activity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("PLATO",plate);
        resultIntent.putExtras(bundle);
        mPositionPressed = position;
        getActivity().startActivityForResult(resultIntent,1, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),view,"transition").toBundle());


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public AlertDialog total(){
        int totalCuenta = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

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

    public void showTable(int position){

        mTable = mTables.get(position);
        mAdapter.notifyDataSetChanged();


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_buttons,menu);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superValue = super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.total:
                AlertDialog total = total();
                total.show();
                return true;
//            case android.R.id.home:
//                getActivity().finish();
//                return true;
        }
        return superValue;
    }
}
