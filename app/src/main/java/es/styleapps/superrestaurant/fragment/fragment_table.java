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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

import es.styleapps.superrestaurant.R;
import es.styleapps.superrestaurant.activity.Detail_plate_activity;
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
    private static fragment_table mTableFragment;

    public static fragment_table newInstance(){

        mTableFragment = new fragment_table();
        return mTableFragment;

    }

    //En el caso de que este instanciado ya
    public static fragment_table getInstance(){

        if (mTableFragment != null){
            return mTableFragment;
        }else{

            return newInstance();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Por si acaso en algun momento es necesario
        if (savedInstanceState != null){
            mPlates = (LinkedList<Plate>) savedInstanceState.getSerializable(mTable.getTableNumber());

        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_table_list, container, false);

        int position = getActivity().getIntent().getIntExtra(getString(R.string.EXTRA_TABLESELECT),-1);

        mTables = Tables.getTables();

        if (position != -1) {

            mTablePressed = position;

            mTable = mTables.get(mTablePressed);

        } else if (getArguments() != null) {

                if (getArguments().getSerializable(getString(R.string.EXTRA_TABLESELECTED)) != null) {

                    mTablePressed = getArguments().getInt(getString(R.string.EXTRA_POSITIONTABLECHANGE));

                } else {
                    mTablePressed = getArguments().getInt(getString(R.string.EXTRA_POSITION), 0);
                }

                mTable = mTables.get(mTablePressed);

            } else {


                mTable = mTables.get(0);

            }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setTitle(mTable.getTableNumber());


        if (mTable != null) {

            mPlates = mTable.getPlates();

            if (getArguments() != null) {
                if (getArguments().getSerializable(mTable.getTableNumber()) != null) {
                    mPlates = (LinkedList<Plate>) getArguments().getSerializable(mTable.getTableNumber());
                }
            }

            if(savedInstanceState != null){
                mPlates = (LinkedList<Plate>) savedInstanceState.getSerializable(mTable.getTableNumber());

            }




        } else {

            //DATOS TONTOS POR SI NO HAY NADA
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
                    Plate plateExample = (Plate) bundle.getSerializable(getString(R.string.EXTRA_PLATE));
                    mPlates.add(plateExample);
                    Tables.setTables(mTables);
                    mAdapter.notifyItemInserted(mPlates.size());
                    fragment_tables_list.adapterTable.notifyDataSetChanged();


                }
                if (resultCode == 2) {

                    Bundle bundle = data.getExtras();
                    Plate plateExample = (Plate) bundle.getSerializable(getString(R.string.EXTRA_EXTRAS));
                    mPlates.set(mPositionPressed, plateExample);
                    mAdapter.notifyItemChanged(mPositionPressed);
                    Tables.setTables(mTables);
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
        bundle.putSerializable(getString(R.string.EXTRA_ACTUALTABLE), mTable);
        bundle.putSerializable(getString(R.string.EXTRA_PLATO),plate);
        bundle.putInt(getString(R.string.EXTRA_POSITIONTABLE),mTablePressed);
        mPositionPressed = position;
        bundle.putInt(getString(R.string.EXTRA_POSITIONPLATE),mPositionPressed);
        resultIntent.putExtras(bundle);
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

    @Override
    public void onDetach() {
        super.onDetach();
        Bundle bundle = new Bundle();

            if (mTable != null) {
                bundle.putSerializable(mTable.getTableNumber(), mPlates);
            }
        mTable.setPlates(mPlates);
        Tables.setTables(mTables);
        fragment_tables_list.adapterTable.notifyDataSetChanged();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (mTable != null) {
               mPlates = (LinkedList<Plate>) bundle.getSerializable(mTable.getTableNumber());
            }
        }
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
        }
        return superValue;
    }
}
