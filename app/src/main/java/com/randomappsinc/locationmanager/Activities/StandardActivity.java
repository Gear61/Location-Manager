package com.randomappsinc.locationmanager.Activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;

import com.randomappsinc.locationmanager.R;
import com.randomappsinc.locationmanager.Utils.UIUtils;

/**
 * Created by alexanderchiou on 4/9/17.
 */

public class StandardActivity extends AppCompatActivity {
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        UIUtils.hideKeyboard(this);
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);
    }

    @Override
    public void finish() {
        UIUtils.hideKeyboard(this);
        super.finish();
        overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}