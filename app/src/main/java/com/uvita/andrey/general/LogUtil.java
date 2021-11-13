package com.uvita.andrey.general;

import android.util.Log;


import com.uvita.andrey.BuildConfig;

public final class LogUtil {
    private static final String LOG_PREFIX = "aag_";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 23; // see https://developer.android.com/reference/android/util/Log#isLoggable(java.lang.String,%20int)

    private LogUtil() {
    }

    public static String makeLogTag(String str) {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH)
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        return LOG_PREFIX + str;
    }

    /**
     * WARNING: Don't use this when obfuscating class names with Proguard!
     */
    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

    public static void LOGD(final String tag, String message) {
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, message);
        }
    }

    public static void LOGD(final String tag, String message, Throwable cause) {
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, message, cause);
        }
    }

    public static void LOGV(final String tag, String message) {
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(tag, message);
        }
    }

    public static void LOGV(final String tag, String message, Throwable cause) {
        if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(tag, message, cause);
        }
    }

    public static void LOGI(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void LOGI(String tag, String message, Throwable cause) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message, cause);
        }
    }

    public static void LOGW(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message, new Throwable()); // create a stacktrace
        } else {
            firebaseLog(Log.WARN, tag, message);
        }
    }

    public static void LOGW(String tag, String message, Throwable cause) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message, cause);
        } else {
            firebaseLog(Log.WARN, tag, message);
        }
    }

    public static void LOGE(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message, new Throwable()); // create a stacktrace
        } else {
            firebaseLog(Log.ERROR, tag, message);
        }
    }

    public static void LOGE(String tag, String message, Throwable cause) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message, cause);
        } else {
            firebaseLog(Log.ERROR, tag, message);
        }
    }

    private static void firebaseLog(int priority, String tag, String message) {
        try {
//            Crashlytics.log(priority, tag, message);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(tag, message, e);
            }
        }
    }
}
