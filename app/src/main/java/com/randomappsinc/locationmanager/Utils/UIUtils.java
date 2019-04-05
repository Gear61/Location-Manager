package com.randomappsinc.locationmanager.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import com.google.android.material.snackbar.Snackbar;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconDrawable;
import com.randomappsinc.locationmanager.R;

/**
 * Created by alexanderchiou on 4/9/17.
 */

public class UIUtils {
    public static void showSnackbar(View parent, String message) {
        Context context = MyApplication.getAppContext();
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder(message);
        spanBuilder.setSpan(
                new ForegroundColorSpan(Color.WHITE),
                0,
                message.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        Snackbar snackbar = Snackbar.make(parent, spanBuilder, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void loadMenuIcon(Menu menu, int itemId, Icon icon) {
        menu.findItem(itemId).setIcon(
                new IconDrawable(MyApplication.getAppContext(), icon)
                        .colorRes(R.color.white)
                        .actionBarSize());
    }
}
