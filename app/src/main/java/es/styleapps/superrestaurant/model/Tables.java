package es.styleapps.superrestaurant.model;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;

/**
 * Created by jlgarciaap on 10/12/16.
 */

public class Tables {

    private LinkedList<Table> mTables;

    public LinkedList<Table> getTables() {
        return mTables;
    }

    public void setTables(LinkedList<Table> tables) {
        mTables = tables;
    }

    public Tables() {

        mTables = new LinkedList<>();

        mTables.add(new Table("Mesa 1"));
        mTables.add(new Table("Mesa 2"));
        mTables.add(new Table("Mesa 3"));
        mTables.add(new Table("Mesa 4"));
        mTables.add(new Table("Mesa 5"));
        mTables.add(new Table("Mesa 6"));
        mTables.add(new Table("Mesa 7"));
        mTables.add(new Table("Mesa 8"));

    }
}
