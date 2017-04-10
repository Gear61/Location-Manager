package com.randomappsinc.locationmanager.Persistence;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by alexanderchiou on 4/9/17.
 */

public class LocationDO extends RealmObject {
    @PrimaryKey
    private String title;

    private String address;
    private double latitude;
    private double longitude;
    private long timeAdded;
}
