package com.wells.fastframe.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;

import com.wells.fastframe.toolbox.T;
import com.wells.fastframe.view.MainActivity;

public class CrashHandler implements UncaughtExceptionHandler {
	private BocApp context;
	private static CrashHandler crashHandler;
	private UncaughtExceptionHandler defaultExceptionHandler;

	private CrashHandler() {

	}

	public static CrashHandler getInstance() {
		if (crashHandler == null)
			crashHandler = new CrashHandler();
		return crashHandler;
	}

	public void init(BocApp context) {
		this.context = context;
		defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(thread, ex) && defaultExceptionHandler != null) {
			defaultExceptionHandler.uncaughtException(thread, ex);
		} else {
			// restart(); //其他操作
		}
	}

	@SuppressLint("SimpleDateFormat")
	private boolean handleException(Thread thread, Throwable ex) {
		final StringBuilder sb = new StringBuilder();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
		Date firstDate = new Date(System.currentTimeMillis()); // 第一次创建文件，也就是开始日期
		String str = formatter.format(firstDate);
		sb.append(str + "\n"); // 把当前的日期写入到字符串中
		sb.append("Version code is ");
		sb.append(Build.VERSION.SDK_INT + "\n");// 设备的Android版本号
		sb.append("Model is ");
		sb.append(Build.MODEL + "\n");// 设备型号
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		ex.printStackTrace(pw);
		String errorresult = writer.toString();
		sb.append(errorresult);
		sb.append("\n");
		try {
			String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Android"
					+ File.separator + "data" + File.separator + context.getPackageName() + File.separator + "Log"
					+ File.separator;
			File fileDir = new File(savePath);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			File files = new File(fileDir, "error.log");
			if (!files.exists()) {
				files.createNewFile();
			}
			System.out.println(files.getAbsolutePath());
			FileOutputStream fileOutputStream = new FileOutputStream(files, true);
			fileOutputStream.write(sb.toString().getBytes());
			fileOutputStream.close();

			// 文件大小限制在1M,超过1M自动删除
			@SuppressWarnings("resource")
			FileInputStream fileInputStream = new FileInputStream(files);
			int sizeK = fileInputStream.available() / 1024; // 单位是KB
			int totalSize = 1 * 1024;
			if (sizeK > totalSize) {
				boolean b = files.delete();
				if (b) { // 删除成功,重新创建一个文件
					@SuppressWarnings("unused")
					File filesTwo = new File(fileDir, "error.log");
					if (!files.exists()) {
						files.createNewFile();
					}
				} else {
					// 删除失败
					@SuppressWarnings("resource")
					FileOutputStream fileOutputStream2 = new FileOutputStream(files);
					fileOutputStream2.write("  ".getBytes()); // 写入一个空格进去
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// restart();
		return true;
	}

	@SuppressWarnings("unused")
	private void restart() {
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				T.show(context, "很抱歉,程序出现异常,1秒后重新启动.");
				Looper.loop();
			}
		}.start();

		Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
		PendingIntent restartIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, intent,
				Intent.FLAG_ACTIVITY_NEW_TASK);
		// 退出程序
		AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用
		BocApp.getInstance().exit();
	}
}
