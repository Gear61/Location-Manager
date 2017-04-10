package com.randomappsinc.locationmanager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.randomappsinc.locationmanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexanderchiou on 4/9/17.
 */

public class SettingsAdapter extends BaseAdapter {
    private String[] itemNames;
    private String[] itemIcons;
    private Context context;

    public SettingsAdapter(Context context) {
        this.context = context;
        this.itemNames = context.getResources().getStringArray(R.array.settings_options);
        this.itemIcons = context.getResources().getStringArray(R.array.settings_icons);
    }

    @Override
    public int getCount() {
        return itemNames.length;
    }

    @Override
    public String getItem(int position) {
        return itemNames[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class SettingsViewHolder {
        @BindView(R.id.icon) TextView itemIcon;
        @BindView(R.id.option) TextView itemName;

        public SettingsViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void loadItem(int position) {
            itemName.setText(itemNames[position]);
            itemIcon.setText(itemIcons[position]);
        }
    }

    public View getView(int position, View view, ViewGroup parent) {
        SettingsViewHolder holder;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.settings_item_cell, parent, false);
            holder = new SettingsViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (SettingsViewHolder) view.getTag();
        }

        holder.loadItem(position);

        return view;
    }
}
