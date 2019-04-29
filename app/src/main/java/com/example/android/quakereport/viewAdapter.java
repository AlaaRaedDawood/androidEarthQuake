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
        magnitudeTextView.setText(Double.toString(earthquake.getMagnitude()));
        locationTextView.setText(earthquake.getCity());
        dateTextView.setText(earthquake.getDate());
        timeTextView.setText(earthquake.getTime());

        return view;
    }


}


