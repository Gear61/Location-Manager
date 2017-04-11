package com.randomappsinc.locationmanager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.randomappsinc.locationmanager.Models.SavedLocation;
import com.randomappsinc.locationmanager.Persistence.DatabaseManager;
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
    private List<SavedLocation> savedLocations;
    private View noLocations;

    public LocationsAdapter(Context context, View noLocations) {
        this.context = context;
        this.savedLocations = new ArrayList<>();
        this.noLocations = noLocations;
        resyncWithDB();
    }

    public void resyncWithDB() {
        savedLocations.clear();
        savedLocations.addAll(DatabaseManager.get().getLocations());
        notifyDataSetChanged();
        noLocations.setVisibility(savedLocations.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getCount() {
        return savedLocations.size();
    }

    @Override
    public SavedLocation getItem(int position) {
        return savedLocations.get(position);
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
            SavedLocation savedLocation = getItem(position);
            title.setText(savedLocation.getTitleText());
            address.setText(savedLocation.getAddressText());
            latLong.setText(savedLocation.getLatLongText());
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
