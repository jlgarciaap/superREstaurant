package es.styleapps.superrestaurant.model;

import java.util.LinkedList;

import es.styleapps.superrestaurant.R;

/**
 * Created by jlgarciaap on 10/12/16.
 */

public class Table {

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

        mPlates.add(new Plate("Huevos Fritos", "Pos unos huevos con papas","Espero que no", R.drawable.ico_09, 40));
        mPlates.add(new Plate("Huevos Fritos2", "Pos unos huevos con papas","Espero que no", R.drawable.ico_09, 20));
        mPlates.add(new Plate("Huevos Fritos3", "Pos unos huevos con papas","Espero que no", R.drawable.ico_09, 30));
        mPlates.add(new Plate("Huevos Fritos4", "Pos unos huevos con papas","Espero que no", R.drawable.ico_09, 50));
        mPlates.add(new Plate("Huevos Fritos5", "Pos unos huevos con papas","Espero que no", R.drawable.ico_09, 60));


    }


    public LinkedList<Plate> getPlates() {
        return mPlates;
    }

    @Override
    public String toString() {
        return getTableNumber();
    }
}
