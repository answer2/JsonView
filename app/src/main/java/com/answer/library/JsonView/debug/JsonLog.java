package com.answer.library.JsonView.debug;
import android.util.Log;

public class JsonLog {

    public static final String TAG = "JsonLog";

    private static String table = "JsonView_";

    private static boolean isDebug = true;

    public static void setIsDebug(boolean isDebug) {
        JsonLog.isDebug = isDebug;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void v(String tag, String message) {
        if (isDebug)Log.v(table + tag, message);
    }

    public static void d(String tag, String message) {
        if (isDebug)Log.d(table + tag, message);
    }

    public static void i(String tag, String message) {
        if (isDebug)Log.i(table + tag, message);
    }

    public static void w(String tag, String message) {
        if (isDebug)Log.w(table + tag, message);
    }

    public static void e(String tag, String message) {
        if (isDebug)Log.e(table + tag, message);
    }
	
	public static void e(String tag, String message, Throwable e) {
        if (isDebug)Log.e(table + tag, message, e);
    }
}
