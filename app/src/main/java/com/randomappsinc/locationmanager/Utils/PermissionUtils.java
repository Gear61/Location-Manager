package com.randomappsinc.locationmanager.Utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.legacy.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Created by alexanderchiou on 4/9/17.
 */

public class PermissionUtils {
    public static void requestPermission(Activity activity, String permission, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
    }

    public static boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(MyApplication.getAppContext(), permission)
                == PackageManager.PERMISSION_GRANTED;
    }
}
