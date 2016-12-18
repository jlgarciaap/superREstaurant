package es.styleapps.superrestaurant.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;

import es.styleapps.superrestaurant.R;
import es.styleapps.superrestaurant.model.Table;
import es.styleapps.superrestaurant.model.Tables;

/**
 * Created by jlgarciaap on 10/12/16.
 */

public class Tables_list_activity extends AppCompatActivity implements Serializable {


    public static ArrayAdapter<Table> adapterTable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tables_list_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Le decimos a nuestra pantalla que esa es nuestra action bar
        setSupportActionBar(toolbar);

        final Tables tables = new Tables();

        ListView tablesList = (ListView) findViewById(R.id.tables_list);

        adapterTable = new ArrayAdapter<Table>(getBaseContext(),
                android.R.layout.simple_list_item_1, tables.getTables());


        tablesList.setAdapter(adapterTable);

        tablesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


           Intent intent = new Intent(getBaseContext(),Table_activity.class);
              Bundle bundle = new Bundle();
            bundle.putSerializable("TABLEID",i);
               // bundle.putSerializable("TABLESGROUP", (Serializable) adapterTable);
                intent.putExtras(bundle);
               startActivity(intent);

            }
        });



    }


}
