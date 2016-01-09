package com.wells.fastframe.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class BocApp extends Application {

	private String cookies;

	public static List<Activity> activityList = new ArrayList<Activity>();

	private static BocApp instance;

	public static BocApp getInstance() {
		return instance; 
	}

	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}

	private void init() {
		instance = this;
		CrashHandler.getInstance().init(this);

	}

	public void exit() {
		for (Activity activity : activityList) {
			activity.finish();
		}
		System.exit(0);
		System.gc();
	}

	public String getCookies() {
		return cookies != null ? cookies : "";
	}

	public void setCookies(String cookies) {
		this.cookies = cookies;
	}

}
