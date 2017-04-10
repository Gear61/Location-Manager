package com.randomappsinc.locationmanager.Persistence;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder().build());
    }
}
