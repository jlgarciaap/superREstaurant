package es.styleapps.superrestaurant.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.LinkedList;

import es.styleapps.superrestaurant.R;
import es.styleapps.superrestaurant.fragment.fragment_table;
import es.styleapps.superrestaurant.fragment.fragment_tables_list;
import es.styleapps.superrestaurant.model.Plate;
import es.styleapps.superrestaurant.model.Table;
import es.styleapps.superrestaurant.model.Tables;

/**
 * Created by jlgarciaap on 10/12/16.
 */

public class Table_activity extends AppCompatActivity {

    private fragment_table mTable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_table_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getFragmentManager();

        //Controlamos si existe el fragment
        if(findViewById(R.id.fragment_table)!= null) {

            if (fm.findFragmentById(R.id.fragment_table) == null) {

                mTable = fragment_table.getInstance();
                fm.beginTransaction().add(R.id.fragment_table, mTable).commit();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1: {
                if (resultCode == 2) {
                    //A la vuelta de la actividad obtenemos datos y guardamos los que necesitamos
                    //Para evitar la persistencia. Esto esta repetido, se podria intentar sacar a una
                    //clase pero para facilitar la visualizacion en cada clase he preferido dejarlo
                    Bundle bundle = data.getExtras();
                    Plate plateExample = (Plate) bundle.getSerializable(getString(R.string.EXTRA_EXTRAS));
                    Table tableSelected = (Table) bundle.getSerializable(getString(R.string.EXTRA_TABLESELECTED));
                    LinkedList<Plate> platesTable = tableSelected.getPlates();
                    int position = bundle.getInt(getString(R.string.EXTRA_POSITONPLATESELECTED));
                    int positionTable = bundle.getInt(getString(R.string.EXTRA_POSITIONTABLECHANGE));
                    platesTable.set(position, plateExample);
                    LinkedList<Table> tables = Tables.getTables();
                    tables.set(positionTable, tableSelected);
                    Tables.setTables(tables);
                    fragment_tables_list.adapterTable.notifyDataSetChanged();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragment_table fragmentTableNew = new fragment_table();
                    fragmentTableNew.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragment_table, fragmentTableNew).commit();
                }
            }
        }
    }

}
