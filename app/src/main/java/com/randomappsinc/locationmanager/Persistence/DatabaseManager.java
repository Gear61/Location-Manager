package com.randomappsinc.locationmanager.Persistence;

import android.location.Location;

import com.randomappsinc.locationmanager.Models.SavedLocation;
import com.randomappsinc.locationmanager.Utils.LocationUtils;
import com.randomappsinc.locationmanager.Utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

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
        List<SavedLocationDO> savedLocationDOs = getRealm().where(SavedLocationDO.class).findAll();
        for (SavedLocationDO savedLocationDO : savedLocationDOs) {
            SavedLocation savedLocation = new SavedLocation();
            savedLocation.setTitle(savedLocationDO.getTitle());
            savedLocation.setAddress(savedLocationDO.getAddress());
            savedLocation.setLatitude(savedLocationDO.getLatitude());
            savedLocation.setLongitude(savedLocationDO.getLongitude());
            savedLocation.setTimeAdded(savedLocationDO.getTimeAdded());
            locations.add(savedLocation);
        }
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

    public boolean isDuplicate(String title) {
        return getRealm().where(SavedLocationDO.class)
                .equalTo("title", title)
                .findFirst() != null;
    }
}
