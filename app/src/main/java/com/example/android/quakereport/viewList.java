package com.example.android.quakereport;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class viewList extends ArrayAdapter<earthquakes> {
    private ArrayList<earthquakes> earthQuakelist;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public viewList (List<earthquakes> earthQuakelist, Context context) {
       super(context , 0 ,earthQuakelist );
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView ;
        if(view == null) {
            view = mLayoutInflater.inflate(R.layout.listview,parent,false);

        }
        earthquakes earthquake = getItem(position);
        TextView magnitudeTextView = (TextView) view.findViewById(R.id.magnitude);
        TextView locationTextView = (TextView) view.findViewById(R.id.country);
        TextView dateTextView = (TextView) view.findViewById(R.id.date);

        magnitudeTextView.setText(Float.toString(earthquake.getMagnitude()));
        locationTextView.setText(earthquake.getCity());
        dateTextView.setText(earthquake.getDate());


        return view;
    }


    private class Holder
    {
        TextView name;
        TextView price;
        TextView stock;
    }
}


