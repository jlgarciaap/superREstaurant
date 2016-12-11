package es.styleapps.superrestaurant.model;


import java.io.Serializable;

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

    public Plate(String plateName, String plateDetails, String plateAlergens, int plateImage, float platePrice){

        mPlateName = plateName;
        mPlateAlergens = plateAlergens;
        mPlateDetails = plateDetails;
        mPlateImage = plateImage;
        mPlatePrice = platePrice;

    }


    //Para obtener los nombres en condiciones
    @Override
    public String toString() {
        return getPlateName();
    }
}
