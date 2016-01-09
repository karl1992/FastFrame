package com.wells.fastframe.toolbox;

import com.wells.fastframe.app.Constant;

import android.util.Log;

public class LogUtil {

	/** 日志打印统一tag值 */
	private static final String TAG = "csh";

	private LogUtil() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	// 使用默认的TAG值

	public static void i(String msg) {
		if (Constant.isDebugLog) {
			Log.i(TAG, msg);
		}

	}

	public static void d(String msg) {
		if (Constant.isDebugLog) {
			Log.d(TAG, msg);
		}

	}

	public static void e(String msg) {
		if (Constant.isDebugLog) {
			Log.e(TAG, msg);
		}

	}

	public static void v(String msg) {
		if (Constant.isDebugLog) {
			Log.v(TAG, msg);
		}
	}
	
	//自定义tag值
	
	public static void i(String tag,String msg) {
		if (Constant.isDebugLog) {
			Log.i(tag, msg);
		}
	}
	
	public static void d(String tag,String msg) {
		if (Constant.isDebugLog) {
			Log.d(tag, msg);
		}
	}
	
	public static void e(String tag,String msg) {
		if (Constant.isDebugLog) {
			Log.e(tag, msg);
		}

	}

	public static void v(String tag,String msg) {
		if (Constant.isDebugLog) {
			Log.v(tag, msg);
		}
	}
	
}
