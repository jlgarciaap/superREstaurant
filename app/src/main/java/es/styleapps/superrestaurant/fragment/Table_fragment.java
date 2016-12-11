package es.styleapps.superrestaurant.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import es.styleapps.superrestaurant.R;
import es.styleapps.superrestaurant.model.Plate;
import es.styleapps.superrestaurant.model.Table;

/**
 * Created by jlgarciaap on 10/12/16.
 */

public class Table_fragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);


        //Layout del container
        View root = inflater.inflate(R.layout.fragment_table_list,container,false);

        //Accedemos al listView
        //ListView list = (ListView) root.findViewById(R.id.plate_list);

        //Creamos el modelo
        final Table table = new Table("Mesa 2");


        //Creamos adapter para poner en comun modelo con la lista
        ArrayAdapter<Plate> adapter = new ArrayAdapter<Plate>(getActivity(),
                android.R.layout.simple_list_item_1,
                table.getPlates());
        //Asignamos el adapter a la list
//        list.setAdapter(adapter);

        return root;
    }
}
