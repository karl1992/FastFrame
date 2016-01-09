package com.wells.fastframe.toolbox;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {

	/**
	 * 取得当前网络信息
	 * 
	 * @param context
	 * @return
	 */
	private static NetworkInfo getNetworkInfo(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo();
	}

	/**
	 * 检查是否有网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		return info != null ? info.isAvailable() : false;
	}

	/**
	 * 检查是否是WIFI
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifi(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		return info != null ? (info.getType() == ConnectivityManager.TYPE_WIFI) : false;
	}

	/**
	 * 检查是否是移动网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMobile(Context context) {
		NetworkInfo info = getNetworkInfo(context);
		return info != null ? (info.getType() == ConnectivityManager.TYPE_MOBILE) : false;
	}

}
