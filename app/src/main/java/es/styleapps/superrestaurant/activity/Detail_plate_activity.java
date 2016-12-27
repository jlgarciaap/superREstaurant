package es.styleapps.superrestaurant.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

import es.styleapps.superrestaurant.R;
import es.styleapps.superrestaurant.model.Plate;
import es.styleapps.superrestaurant.model.Table;

/**
 * Created by jlgarciaap on 10/12/16.
 */

public class Detail_plate_activity extends AppCompatActivity implements Serializable{

    private TextView mPlateName;
    private TextView mPlateDescription;
    private TextView mPlatePrice;
    private TextView mPlateAlergens;
    private ImageView mPlateImage;
    private EditText mPlateExtras;
    private Button mPlateSave;
    private TextView mPlateChanges;

    //Guardamos el plate actual para futuros usos
    private Plate mPlate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_plate_activity);
        //Obtenemos el plato que nos pasan
        mPlate = (Plate) getIntent().getSerializableExtra(getString(R.string.EXTRA_PLATO));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(mPlate.getPlateName());
        setSupportActionBar(toolbar);



        mPlateName = (TextView) findViewById(R.id.text_plate_name);
        mPlateDescription = (TextView) findViewById(R.id.textPlateDescription);
        mPlatePrice = (TextView) findViewById(R.id.textPlatePrice);
        mPlateAlergens = (TextView) findViewById(R.id.textPlateAlergens);
        mPlateImage = (ImageView) findViewById(R.id.plateImage);
        mPlateExtras = (EditText) findViewById(R.id.editText);
        mPlateSave = (Button) findViewById(R.id.savePlate);
        mPlateChanges = (TextView) findViewById(R.id.plateChanges);

        //Actualizamos la interfaz
        setPlate(mPlate);


        mPlateSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Guardamos todos los datos que podemos necesitar
                mPlate.setPlateExtras(mPlateExtras.getText().toString());
                Intent intent = getIntent();
                Bundle bundle2 = new Bundle();
                Table selectedTable = (Table) intent.getSerializableExtra(getString(R.string.EXTRA_ACTUALTABLE));
                int positionTable = intent.getIntExtra(getString(R.string.EXTRA_POSITIONTABLE),0);
                int positionPlate = intent.getIntExtra(getString(R.string.EXTRA_POSITIONPLATE),0);
                bundle2.putSerializable(getString(R.string.EXTRA_TABLESELECTED),selectedTable);
                bundle2.putSerializable(getString(R.string.EXTRA_EXTRAS),mPlate);
                bundle2.putInt(getString(R.string.EXTRA_POSITIONTABLECHANGE),positionTable);
                bundle2.putInt(getString(R.string.EXTRA_POSITONPLATESELECTED),positionPlate);
                intent.putExtras(bundle2);
                setResult(2,intent);
                finish();
            }
        });




    }

    public void setPlate(Plate plate) {

        mPlateName.setText(plate.getPlateName());
        mPlateAlergens.setText(plate.getPlateAlergens());
        mPlatePrice.setText(String.valueOf(plate.getPlatePrice()));
        mPlateImage.setImageResource(plate.getPlateImage());
        mPlateDescription.setText(plate.getPlateDetails());
        mPlateChanges.setText(plate.getPlateExtras());


    }


}
