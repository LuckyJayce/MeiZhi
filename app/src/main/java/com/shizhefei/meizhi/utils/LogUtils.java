package com.shizhefei.meizhi.utils;

import android.util.Log;

public class LogUtils {
	public static final boolean DEBUG = true;

	public static void d(String tag, String msg) {
		if (DEBUG) {
			Log.d(tag, msg);
		}
	}

	public static void w(String tag, String msg, Exception e) {
		if (DEBUG) {
			Log.w(tag, msg, e);
		}
	}

}
