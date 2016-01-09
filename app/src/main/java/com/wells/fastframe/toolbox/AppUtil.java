package com.wells.fastframe.toolbox;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class AppUtil {

	public static String getIMEI(Context context) {
		TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = telManager.getDeviceId();
		return TextUtils.isEmpty(deviceId) ? "" : deviceId;
	}

	public static boolean isGpsEnabled(Context paramContext) {
		ConnectivityManager mgrConn = (ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		LocationManager locationManager = (LocationManager) paramContext.getSystemService(Context.LOCATION_SERVICE);
		return ((mgrConn.getActiveNetworkInfo() != null && mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED)
				&& locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
	}

	public static PackageInfo getPackageInfo(Context context) {
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packageInfo = null;
		try {
			packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packageInfo;
	}

	public static String getAppName(Context context) {
		return null != getPackageInfo(context) ? context.getResources().getString(
				getPackageInfo(context).applicationInfo.labelRes) : "";
	}

	public static String getVersionName(Context context) {
		return null != getPackageInfo(context) ? getPackageInfo(context).versionName : "";

	}

	public static int getVersionCode(Context context) {
		return null != getPackageInfo(context) ?  getPackageInfo(context).versionCode : 0;

	}

}
