package es.styleapps.superrestaurant.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import es.styleapps.superrestaurant.R;
import es.styleapps.superrestaurant.model.Table;
import es.styleapps.superrestaurant.model.Tables;

/**
 * Created by jlgarciaap on 10/12/16.
 */

public class Tables_list_activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Le decimos a nuestra pantalla que esa es nuestra action bar
        setSupportActionBar(toolbar);

        setContentView(R.layout.tables_list_activity);

        Tables tables = new Tables();

        ListView tablesList = (ListView) findViewById(R.id.tables_list);

        ArrayAdapter<Table> adapterTable = new ArrayAdapter<Table>(getBaseContext(),
                android.R.layout.simple_list_item_1, tables.getTables());

        tablesList.setAdapter(adapterTable);


    }
}
