package com.ins.linphone.utils;

/**
 * Created by tamphan on 28/8/17.
 */

import android.util.Log;

/**
 * This class defines the Logger
 */

public final class LogUtil {
    private static final String TAG = "LinphoneWrapper";
    private static final boolean DETAIL_ENABLE = true;

    private LogUtil() {
    }

    private static String buildMsg(String msg) {
        StringBuilder buffer = new StringBuilder();

        if (DETAIL_ENABLE) {
            final StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
            buffer.append("[ ");
            buffer.append(Thread.currentThread().getName());
            buffer.append(": ");
            buffer.append(stackTraceElement.getFileName());
            buffer.append(": ");
            buffer.append(stackTraceElement.getLineNumber());
            buffer.append(": ");
            buffer.append(stackTraceElement.getMethodName());
        }

        buffer.append("() ] _____ ");

        buffer.append(msg);

        return buffer.toString();
    }

    public static void v(String msg) {
        Log.v(TAG, buildMsg(msg));
    }

    public static void d(String msg) {
        Log.d(TAG, buildMsg(msg));
    }

    public static void i(String msg) {
        Log.i(TAG, buildMsg(msg));
    }

    public static void w(String msg) {
        Log.w(TAG, buildMsg(msg));
    }

    public static void w(String msg, Exception e) {
        Log.w(TAG, buildMsg(msg), e);
    }

    public static void e(String msg) {
        LogUtil.d( buildMsg(msg));
    }

    public static void e(String msg, Exception e) {
        Log.e(TAG, buildMsg(msg), e);
    }
}
