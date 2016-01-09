package com.wells.fastframe.toolbox;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {
	
	public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";

	@SuppressLint("SimpleDateFormat")
	public static String longToString(long currentTime) {
		Date data = new Date(currentTime);
		return new SimpleDateFormat(FORMAT_DATE_TIME).format(data);
	}
}
