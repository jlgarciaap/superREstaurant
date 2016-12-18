package es.styleapps.superrestaurant.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.LinkedList;

import es.styleapps.superrestaurant.R;

/**
 * Created by jlgarciaap on 10/12/16.
 */

public class Table implements Serializable {

    public String getTableNumber() {
        return mTableNumber;
    }

    public void setTableNumber(String tableNumber) {
        mTableNumber = tableNumber;
    }

    private String mTableNumber;

    private LinkedList<Plate> mPlates;



    public Table(String tableNumber){



        mPlates = new LinkedList<>();

        mTableNumber = tableNumber;

        mPlates.add(new Plate("Huevos Fritos", "Pos unos huevos con papas","Espero que no", R.drawable.huevoschorizo, 40));
        mPlates.add(new Plate("Huevos Fritos2", "Pos unos huevos con papas","Espero que no", R.drawable.spaghetti, 20));



    }

    public void setPlate(Plate plate) {

        mPlates.add(plate);
    }

    public LinkedList<Plate> getPlates() {
        return mPlates;
    }

    @Override
    public String toString() {
        return getTableNumber();
    }



}
