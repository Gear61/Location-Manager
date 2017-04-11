package com.randomappsinc.locationmanager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.randomappsinc.locationmanager.Models.Location;
import com.randomappsinc.locationmanager.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 4/10/17.
 */

public class LocationsAdapter extends BaseAdapter {
    private Context context;
    private List<Location> locations;
    private View noLocations;

    public LocationsAdapter(Context context, View noLocations) {
        this.context = context;
        this.noLocations = noLocations;
        resyncWithDB();
    }

    public void resyncWithDB() {
        locations = new ArrayList<>();

        Location firstPlace = new Location();
        firstPlace.setTitle("Home");
        firstPlace.setAddress("4090 Abbey Terrace, Fremont, CA");
        firstPlace.setLatitude(145.02);
        firstPlace.setLongitude(-53.03);

        Location secondPlace = new Location();
        firstPlace.setTitle("Haven");
        firstPlace.setAddress("3728 Haven Avenue, Fremont, CA");
        firstPlace.setLatitude(145.02);
        firstPlace.setLongitude(-53.03);

        Location thirdPlace = new Location();
        firstPlace.setTitle("Haven");
        firstPlace.setAddress("3728 Haven Avenue, Fremont, CA");
        firstPlace.setLatitude(145.02);
        firstPlace.setLongitude(-53.03);

        Location fourthPlace = new Location();
        firstPlace.setTitle("Haven");
        firstPlace.setAddress("3728 Haven Avenue, Fremont, CA");
        firstPlace.setLatitude(145.02);
        firstPlace.setLongitude(-53.03);

        Location fifthPlace = new Location();
        firstPlace.setTitle("Haven");
        firstPlace.setAddress("3728 Haven Avenue, Fremont, CA");
        firstPlace.setLatitude(145.02);
        firstPlace.setLongitude(-53.03);

        Location sixthPlace = new Location();
        firstPlace.setTitle("Haven");
        firstPlace.setAddress("3728 Haven Avenue, Fremont, CA");
        firstPlace.setLatitude(145.02);
        firstPlace.setLongitude(-53.03);

        Location seventhPlace = new Location();
        firstPlace.setTitle("Haven");
        firstPlace.setAddress("3728 Haven Avenue, Fremont, CA");
        firstPlace.setLatitude(145.02);
        firstPlace.setLongitude(-53.03);

        Location eighthPlace = new Location();
        firstPlace.setTitle("Haven");
        firstPlace.setAddress("3728 Haven Avenue, Fremont, CA");
        firstPlace.setLatitude(145.02);
        firstPlace.setLongitude(-53.03);

        locations.add(firstPlace);
        locations.add(secondPlace);
        locations.add(thirdPlace);
        locations.add(fourthPlace);
        locations.add(fifthPlace);
        locations.add(sixthPlace);
        locations.add(seventhPlace);
        locations.add(eighthPlace);

        notifyDataSetChanged();
        noLocations.setVisibility(locations.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getCount() {
        return locations.size();
    }

    @Override
    public Location getItem(int position) {
        return locations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class SettingsViewHolder {
        @BindView(R.id.location_title) TextView title;
        @BindView(R.id.address) TextView address;
        @BindView(R.id.latlong) TextView latLong;

        public SettingsViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void loadItem(int position) {
            Location location = getItem(position);
            title.setText(location.getTitleText());
            address.setText(location.getAddressText());
            latLong.setText(location.getLatLongText());
        }
    }

    public View getView(int position, View view, ViewGroup parent) {
        SettingsViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.location_cell, parent, false);
            holder = new SettingsViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (SettingsViewHolder) view.getTag();
        }
        holder.loadItem(position);
        return view;
    }
}
