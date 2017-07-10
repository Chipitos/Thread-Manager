package com.threadmanager.utils;

import com.threadmanager.App;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String getFormattedTime(long input) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSSSSS", Locale.getDefault());
        return formatter.format(new Date(input));
    }

    public static String getString(int id) {
        return App.getContext().getResources().getString(id);
    }
}
