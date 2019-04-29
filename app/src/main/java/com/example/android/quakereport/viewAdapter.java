package com.example.android.quakereport;


import android.content.Context;
import android.graphics.drawable.GradientDrawable;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class viewAdapter extends ArrayAdapter<earthquakes> {
    private ArrayList<earthquakes> earthQuakelist;
  //  private LayoutInflater mLayoutInflater;
    private Context mContext;

    public viewAdapter(List<earthquakes> earthQuakelist, Context context) {
       super(context , 0 ,earthQuakelist );
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView ;
        if(view == null) {
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.listview, parent, false);


        }
        earthquakes earthquake = getItem(position);

        TextView magnitudeTextView = (TextView) view.findViewById(R.id.magnitude);
        TextView locationTextView = (TextView) view.findViewById(R.id.location);
        TextView dateTextView = (TextView) view.findViewById(R.id.date);
        TextView timeTextView = (TextView) view.findViewById(R.id.time);
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        magnitudeTextView.setText(magnitudeFormat.format(earthquake.getMagnitude()));

            locationTextView.setText(earthquake.getCity());



        dateTextView.setText(earthquake.getDate());
        timeTextView.setText(earthquake.getTime());
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(earthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        return view;
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }


}


