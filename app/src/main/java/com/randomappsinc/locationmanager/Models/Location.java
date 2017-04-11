package com.randomappsinc.locationmanager.Models;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;

import com.randomappsinc.locationmanager.R;
import com.randomappsinc.locationmanager.Utils.MyApplication;

/**
 * Created by alexanderchiou on 4/10/17.
 */

public class Location {
    private String title;
    private String address;
    private double latitude;
    private double longitude;
    private long timeAdded;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(long timeAdded) {
        this.timeAdded = timeAdded;
    }

    public Spanned getTitleText() {
        Context context = MyApplication.getAppContext();
        return Html.fromHtml("<b>" + context.getString(R.string.title_prefix) + "</b>" + title);
    }

    public Spanned getAddressText() {
        Context context = MyApplication.getAppContext();
        return Html.fromHtml("<b>" + context.getString(R.string.address_prefix) + "</b>" + address);
    }

    public Spanned getLatLongText() {
        Context context = MyApplication.getAppContext();
        return Html.fromHtml("<b>" + context.getString(R.string.latlong_prefix) + "</b>"
                + String.valueOf(latitude) + ", " + String.valueOf(longitude));
    }
}
