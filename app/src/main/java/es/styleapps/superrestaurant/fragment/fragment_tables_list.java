package es.styleapps.superrestaurant.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import es.styleapps.superrestaurant.R;
//mport es.styleapps.superrestaurant.activity.Table_activity;
import es.styleapps.superrestaurant.model.Table;
import es.styleapps.superrestaurant.model.Tables;

/**
 * Created by jlgarciaap on 23/12/16.
 */

public class fragment_tables_list extends Fragment {

    private  ListView tablesList;

    private OnTableSelectedListener mOnTableSelectedListener;

    public static ArrayAdapter<Table> adapterTable;

    public static fragment_tables_list newInstance(){
//        Bundle arguments = new Bundle();
//        arguments.putInt(ARG_CITY_INDEX, cityIndex);

        fragment_tables_list tables_list_fragment = new fragment_tables_list();
//        cityPagerFragment.setArguments(arguments);

        return tables_list_fragment;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_tables_list, container, false);

        final Tables tables = new Tables();

        tablesList = (ListView) root.findViewById(R.id.tables_list);

        adapterTable = new ArrayAdapter<Table>(getActivity().getBaseContext(),
                android.R.layout.simple_list_item_1, tables.getTables());

        tablesList.setAdapter(adapterTable);

        tablesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (mOnTableSelectedListener != null){

                    mOnTableSelectedListener.onTableSelected(tables.getTable(i),i);

                }

//                Intent intent = new Intent(getActivity().getBaseContext(), fragment_table.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("TABLEID", i);
//                // bundle.putSerializable("TABLESGROUP", (Serializable) adapterTable);
//                intent.putExtras(bundle);
//                startActivity(intent);

            }
        });


        return root;
    }


    public interface OnTableSelectedListener{

        void onTableSelected(Table table, int position);

    }


    @Override
    public void onAttach(Activity activity) { //Para versiones antiguas
        super.onAttach(activity);


        if(getActivity() instanceof OnTableSelectedListener){

            mOnTableSelectedListener = (OnTableSelectedListener) getActivity();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(getActivity() instanceof OnTableSelectedListener){

            mOnTableSelectedListener = (OnTableSelectedListener) getActivity();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mOnTableSelectedListener = null;

    }


}
