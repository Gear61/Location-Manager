package com.randomappsinc.locationmanager.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.randomappsinc.locationmanager.Models.SavedLocation;
import com.randomappsinc.locationmanager.Persistence.DatabaseManager;
import com.randomappsinc.locationmanager.R;
import com.randomappsinc.locationmanager.Utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alexanderchiou on 4/10/17.
 */

public class LocationsAdapter extends BaseAdapter {
    private Context context;
    private List<SavedLocation> savedLocations;
    private View noLocations;
    private View parent;

    public LocationsAdapter(Context context, View noLocations, View parent) {
        this.context = context;
        this.savedLocations = new ArrayList<>();
        this.noLocations = noLocations;
        this.parent = parent;
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

        private int position;

        public SettingsViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void loadItem(int position) {
            this.position = position;
            SavedLocation savedLocation = getItem(position);
            title.setText(savedLocation.getTitleText());
            address.setText(savedLocation.getAddressText());
            latLong.setText(savedLocation.getLatLongText());
        }

        @OnClick(R.id.delete_icon)
        public void deleteLocation() {
            final String title = getItem(position).getTitle();
            String confirm = String.format(context.getString(R.string.confirm_delete), "\"" + title + "\"");

            new MaterialDialog.Builder(context)
                    .content(confirm)
                    .positiveText(R.string.yes)
                    .negativeText(android.R.string.no)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            DatabaseManager.get().removeLocation(title);
                            resyncWithDB();
                            UIUtils.showSnackbar(parent, context.getString(R.string.location_deleted));
                        }
                    })
                    .show();
        }

        @OnClick(R.id.edit_icon)
        public void editTitle() {
            String hint = context.getString(R.string.location_title);
            final String originalTitle = getItem(position).getTitle();

            new MaterialDialog.Builder(context)
                    .title(R.string.edit_location_title)
                    .alwaysCallInputCallback()
                    .inputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                    .input(hint, "", new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                            String title = input.toString().trim();
                            boolean shouldDisable = DatabaseManager.get().isDuplicate(title) || title.isEmpty();
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(!shouldDisable);
                        }
                    })
                    .negativeText(android.R.string.no)
                    .positiveText(R.string.rename)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            String newTitle = dialog.getInputEditText().getText().toString().trim();
                            DatabaseManager.get().renameLocation(originalTitle, newTitle);
                            resyncWithDB();
                            UIUtils.showSnackbar(parent, context.getString(R.string.location_renamed));
                        }
                    })
                    .show();
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
