package com.randomappsinc.locationmanager.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.IoniconsIcons;
import com.randomappsinc.locationmanager.Persistence.PreferencesManager;
import com.randomappsinc.locationmanager.R;
import com.randomappsinc.locationmanager.Utils.PermissionUtils;
import com.randomappsinc.locationmanager.Utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

public class MainActivity extends StandardActivity {
    @BindView(R.id.parent) View parent;
    @BindView(R.id.add_location) FloatingActionButton addLocation;

    private Context context;
    private boolean locationFetched;
    private Handler locationChecker;
    private Runnable locationCheckTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Kill activity if it's not on top of the stack due to Samsung bug
        if (!isTaskRoot() && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null && getIntent().getAction().equals(Intent.ACTION_MAIN)) {
            finish();
            return;
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;

        addLocation.setImageDrawable(new IconDrawable(this, IoniconsIcons.ion_location).colorRes(R.color.white));

        locationChecker = new Handler();
        locationCheckTask = new Runnable() {
            @Override
            public void run() {
                SmartLocation.with(context).location().stop();
                if (!locationFetched) {
                    UIUtils.showSnackbar(parent, getString(R.string.auto_location_fail));
                }
            }
        };

        if (PreferencesManager.get().shouldAskToRate()) {
            new MaterialDialog.Builder(this)
                    .cancelable(false)
                    .content(R.string.please_rate)
                    .negativeText(R.string.no_im_good)
                    .positiveText(R.string.will_rate)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            if (getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                                startActivity(intent);
                            }
                        }
                    })
                    .show();
        }
    }

    @OnClick(R.id.add_location)
    public void addLocation() {
        if (PermissionUtils.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            fetchLocation();
        } else {
            PermissionUtils.requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, 1);
        }
    }

    public void fetchLocation() {
        // Cancel previous searches
        SmartLocation.with(this).location().stop();
        locationChecker.removeCallbacks(locationCheckTask);

        if (SmartLocation.with(this).location().state().locationServicesEnabled()) {
            locationFetched = false;
            SmartLocation.with(this).location()
                    .oneFix()
                    .start(new OnLocationUpdatedListener() {
                        @Override
                        public void onLocationUpdated(Location location) {
                            SmartLocation.with(context).location().stop();
                            locationChecker.removeCallbacks(locationCheckTask);
                            locationFetched = true;
                        }
                    });
            locationChecker.postDelayed(locationCheckTask, 10000L);
        } else {
            showLocationServicesDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        // If they give us access to their location, fetch location, otherwise, pester them for permission again
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchLocation();
        } else {
            PermissionUtils.requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, 1);

        }
    }

    private void showLocationServicesDialog() {
        new MaterialDialog.Builder(this)
                .content(R.string.location_services_needed)
                .negativeText(android.R.string.cancel)
                .positiveText(android.R.string.yes)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        UIUtils.loadMenuIcon(menu, R.id.settings, IoniconsIcons.ion_android_settings);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
