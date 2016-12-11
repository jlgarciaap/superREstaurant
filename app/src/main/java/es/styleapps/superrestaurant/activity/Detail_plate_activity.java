package es.styleapps.superrestaurant.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import es.styleapps.superrestaurant.R;
import es.styleapps.superrestaurant.model.Plate;

/**
 * Created by jlgarciaap on 10/12/16.
 */

public class Detail_plate_activity extends AppCompatActivity{

    private TextView mPlateName;
    private TextView mPlateDescription;
    private TextView mPlatePrice;
    private TextView mPlateAlergens;
    private ImageView mPlateImage;

    //Guardamos el plate actual para futuros usos
    private Plate mPlate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_plate_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Le decimos a nuestra pantalla que esa es nuestra action bar
        setSupportActionBar(toolbar);

        Plate examplePlate = new Plate("Huevos Fritos", "Pos unos huevos con papas","Espero que no",R.drawable.ico_09, 20);

        mPlateName = (TextView) findViewById(R.id.text_plate_name);
        mPlateDescription = (TextView) findViewById(R.id.textPlateDescription);
        mPlatePrice = (TextView) findViewById(R.id.textPlatePrice);
        mPlateAlergens = (TextView) findViewById(R.id.textPlateAlergens);
        mPlateImage = (ImageView) findViewById(R.id.plateImage);

        //Actualizamos la interfaz
        setPlate(examplePlate);


    }

    public void setPlate(Plate plate) {

        mPlateName.setText(plate.getPlateName());
        mPlateAlergens.setText(plate.getPlateAlergens());
        mPlatePrice.setText(String.valueOf(plate.getPlatePrice()));
        mPlateImage.setImageResource(plate.getPlateImage());
        mPlateDescription.setText(plate.getPlateDetails());
        //Lo guardamos por si acaso
        mPlate = plate;

    }
}
