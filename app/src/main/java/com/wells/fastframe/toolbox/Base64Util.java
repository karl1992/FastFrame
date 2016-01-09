package com.wells.fastframe.toolbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.util.Base64;

public class Base64Util {
	
	/**
	 * 将文件转成base64 字符串
	 * 
	 * @param path
	 * @return
	 */
	public static String encodeBase64File(String path) {
		File file = new File(path);
		FileInputStream inputFile = null;
		byte[] buffer = new byte[(int) file.length()];
		try {
			inputFile = new FileInputStream(file);
			inputFile.read(buffer);
			inputFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Base64.encodeToString(buffer, Base64.DEFAULT);
	}

	/**
	 * 将base64字符解码保存文件
	 * 
	 * @param base64Code
	 * @param savePath
	 * @throws Exception
	 */
	public static void decoderBase64File(String base64Code, String savePath) throws Exception {
		byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
		FileOutputStream out = new FileOutputStream(savePath);
		out.write(buffer);
		out.close();
	}

}
