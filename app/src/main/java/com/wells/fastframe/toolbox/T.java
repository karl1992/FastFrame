package com.wells.fastframe.toolbox;

import android.content.Context;
import android.widget.Toast;

public class T {

	/**
	 * 提示
	 * @param context
	 * @param cs
	 */
	public static void show(Context context, CharSequence cs) {
		Toast.makeText(context, cs, Toast.LENGTH_SHORT).show();
	}

}
