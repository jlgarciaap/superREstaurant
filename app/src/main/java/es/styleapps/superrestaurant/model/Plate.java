package es.styleapps.superrestaurant.model;


import java.io.Serializable;

import es.styleapps.superrestaurant.R;

/**
 * Created by jlgarciaap on 10/12/16.
 */

public class Plate implements Serializable{

    public String getPlateName() {
        return mPlateName;
    }

    public void setPlateName(String plateName) {
        mPlateName = plateName;
    }

    public String getPlateDetails() {
        return mPlateDetails;
    }

    public void setPlateDetails(String plateDetails) {
        mPlateDetails = plateDetails;
    }

    public String getPlateAlergens() {
        return mPlateAlergens;
    }

    public void setPlateAlergens(String plateAlergens) {
        mPlateAlergens = plateAlergens;
    }

    public int getPlateImage() {
        return mPlateImage;
    }

    public void setPlateImage(int plateImage) {
        mPlateImage = plateImage;
    }

    public float getPlatePrice() {
        return mPlatePrice;
    }

    public void setPlatePrice(float platePrice) {
        mPlatePrice = platePrice;
    }

    private String mPlateName;
    private String mPlateDetails;
    private String mPlateAlergens;
    private int mPlateImage;
    private float mPlatePrice;
    private String mPlateExtras;

    public Plate(String plateName, String plateDetails, String plateAlergens, int plateImage, float platePrice){

        mPlateName = plateName;
        mPlateAlergens = plateAlergens;
        mPlateDetails = plateDetails;
        mPlatePrice = platePrice;
        mPlateExtras = "";

        switch (plateImage){
            case 1:
               mPlateImage = R.drawable.spaghetti;
                break;
            case 2:
                mPlateImage = R.drawable.huevoschorizo;
                break;
            case 3:
                mPlateImage = R.drawable.emperador;
                break;
            case 4:
                mPlateImage = R.drawable.solternera;
                break;
            default:
                mPlateImage = R.drawable.huevoschorizo;
        }


    }

    public String getPlateExtras() {
        return mPlateExtras;
    }

    public void setPlateExtras(String plateExtras) {
        mPlateExtras = plateExtras;
    }

    //Para obtener los nombres en condiciones
    @Override
    public String toString() {
        return getPlateName();
    }
}
