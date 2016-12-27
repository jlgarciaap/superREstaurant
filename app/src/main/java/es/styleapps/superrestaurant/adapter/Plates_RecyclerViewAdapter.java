package es.styleapps.superrestaurant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

import es.styleapps.superrestaurant.R;
import es.styleapps.superrestaurant.model.Plate;

/**
 * Created by jlgarciaap on 11/12/16.
 */

public class Plates_RecyclerViewAdapter extends RecyclerView.Adapter<Plates_RecyclerViewAdapter.PlatesViewHolder> {

   private LinkedList<Plate> mPlates;

    private Context mContext;

    private OnPlateClickListener mOnPlateClickListener;//Para el listener del boton



    public Plates_RecyclerViewAdapter (LinkedList<Plate> plates, Context context, OnPlateClickListener onPlateClickListener){
        super();
        mPlates = plates;
        mContext = context;
        mOnPlateClickListener = onPlateClickListener; //Para que t odo sea boton
    }


    public static class PlatesViewHolder extends RecyclerView.ViewHolder{

        private ImageView mPlateImage;
        private TextView mPlateName;
        private TextView mPlatePrice;
        private View mView; //para pasar la vista por el listener
        private ImageView mAlergenImage;

        public PlatesViewHolder (View view){

            super(view);
            mPlateImage = (ImageView) view.findViewById(R.id.plate_recycler_image);
            mPlateName = (TextView) view.findViewById(R.id.plate_recycler_name);
            mPlatePrice = (TextView) view.findViewById(R.id.plate_recycler_price);
            mAlergenImage = (ImageView) view.findViewById(R.id.plate_alergens);
            mView = view;

        }

        public View getView(){

            return mView;
        }

    }



    @Override
    public PlatesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_plate_list, parent, false);

        return new PlatesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlatesViewHolder holder, final int position) {

         int precio = (int) mPlates.get(position).getPlatePrice();
         String alergens =  mPlates.get(position).getPlateAlergens();
        holder.mPlatePrice.setText(String.valueOf(precio + "€" ));
        holder.mPlateName.setText(mPlates.get(position).getPlateName());
        holder.mPlateImage.setImageResource(mPlates.get(position).getPlateImage());

        //Para el icono de alergenos
        switch (alergens) {
            case "fish":
                holder.mAlergenImage.setImageResource(R.drawable.fish_icon2);
                break;
            case "gluten":
                holder.mAlergenImage.setImageResource(R.drawable.gluten_icon2);
                break;
            case "egg":
                holder.mAlergenImage.setImageResource(R.drawable.egg_icon);
                break;
            default:
                holder.mAlergenImage.setImageResource(R.drawable.allgood_icon);
                break;
        }


        holder.getView().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if (mOnPlateClickListener != null){

                    mOnPlateClickListener.onPlateClick(position,mPlates.get(position), view);

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mPlates.size();
    }

    public interface OnPlateClickListener {

        public void onPlateClick(int position, Plate plate, View view);
    }


}
