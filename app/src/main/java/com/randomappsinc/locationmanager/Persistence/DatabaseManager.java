package com.randomappsinc.locationmanager.Persistence;

import android.location.Location;

import com.randomappsinc.locationmanager.Models.SavedLocation;
import com.randomappsinc.locationmanager.Utils.LocationUtils;
import com.randomappsinc.locationmanager.Utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

/**
 * Created by alexanderchiou on 4/9/17.
 */

public class DatabaseManager {
    private static DatabaseManager instance;

    public static DatabaseManager get() {
        if (instance == null) {
            instance = getSync();
        }
        return instance;
    }

    private static synchronized DatabaseManager getSync() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private Realm getRealm() {
        return Realm.getDefaultInstance();
    }

    private DatabaseManager() {
        Realm.init(MyApplication.getAppContext());
    }

    public List<SavedLocation> getLocations() {
        List<SavedLocation> locations = new ArrayList<>();
        List<SavedLocationDO> savedLocationDOs = getRealm()
                .where(SavedLocationDO.class)
                .findAllSorted("timeAdded", Sort.DESCENDING);

        for (SavedLocationDO savedLocationDO : savedLocationDOs) {
            SavedLocation savedLocation = new SavedLocation();
            savedLocation.setTitle(savedLocationDO.getTitle());
            savedLocation.setAddress(savedLocationDO.getAddress());
            savedLocation.setLatitude(savedLocationDO.getLatitude());
            savedLocation.setLongitude(savedLocationDO.getLongitude());
            savedLocation.setTimeAdded(savedLocationDO.getTimeAdded());
            locations.add(savedLocation);
        }

        SavedLocation location1 = new SavedLocation();
        location1.setTitle("Home");
        location1.setAddress("680 Mission St, San Francisco, CA 94105");
        location1.setLatitude(37.786744);
        location1.setLongitude(-122.401752);
        location1.setTimeAdded(0);

        SavedLocation location2 = new SavedLocation();
        location2.setTitle("Work");
        location2.setAddress("1455 Market St, San Francisco, CA 94103");
        location2.setLatitude(37.775228);
        location2.setLongitude(-122.417563);
        location2.setTimeAdded(1);

        SavedLocation location3 = new SavedLocation();
        location3.setTitle("Where I Parked My Car");
        location3.setAddress("123 O'Farrell St, San Francisco, CA 94102");
        location3.setLatitude(37.785767);
        location3.setLongitude(-122.407133);
        location3.setTimeAdded(2);

        locations.add(location3);
        locations.add(location2);
        locations.add(location1);

        return locations;
    }

    public void addLocation(final Location location, final String title) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SavedLocationDO savedLocationDO = new SavedLocationDO();
                savedLocationDO.setTitle(title);
                savedLocationDO.setAddress(LocationUtils.getAddressFromLocation(location));
                savedLocationDO.setLatitude(location.getLatitude());
                savedLocationDO.setLongitude(location.getLongitude());
                savedLocationDO.setTimeAdded(System.currentTimeMillis());
                realm.insert(savedLocationDO);
            }
        });
    }

    public void removeLocation(final String title) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(SavedLocationDO.class).equalTo("title", title).findFirst().deleteFromRealm();
            }
        });
    }

    public void renameLocation(final String oldTitle, final String newTitle) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SavedLocationDO locationDO = realm.where(SavedLocationDO.class)
                        .equalTo("title", oldTitle)
                        .findFirst();
                locationDO.setTitle(newTitle);
            }
        });
    }

    public boolean isDuplicate(String title) {
        return getRealm().where(SavedLocationDO.class)
                .equalTo("title", title)
                .findFirst() != null;
    }
}
