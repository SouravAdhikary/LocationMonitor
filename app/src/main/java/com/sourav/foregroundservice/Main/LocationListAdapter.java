package com.sourav.foregroundservice.Main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sourav.foregroundservice.DB.Location;
import com.sourav.foregroundservice.R;

import java.util.ArrayList;

/**
 * Created by Sourav Adhikary on 30-11-2018.
 */

public class LocationListAdapter extends  RecyclerView.Adapter<LocationListAdapter.ViewHolder> {

    ArrayList<Location> locationList;
    MapsActivity mapsActivity;

    public LocationListAdapter(ArrayList<Location> locationList, MapsActivity mapsActivity) {
        this.locationList = locationList;
        this.mapsActivity = mapsActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationListAdapter.ViewHolder holder, int position) {

        holder.tv_latitude.setText(locationList.get(position).getLatitude());
        holder.tv_longitude.setText(locationList.get(position).getLongitude());
        holder.tv_time.setText(locationList.get(position).getTimeStamp());
        holder.tv_gps_speed.setText(locationList.get(position).getGpsSpeed());
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_latitude;
        TextView tv_longitude;
        TextView tv_time;
        TextView tv_gps_speed;


        public ViewHolder(View view) {
            super(view);

            tv_latitude=(TextView)view.findViewById(R.id.tv_latitude);
            tv_longitude=(TextView)view.findViewById(R.id.tv_longitude);
            tv_time=(TextView)view.findViewById(R.id.tv_time);
            tv_gps_speed=(TextView)view.findViewById(R.id.tv_gps_speed);



        }

    }
}
