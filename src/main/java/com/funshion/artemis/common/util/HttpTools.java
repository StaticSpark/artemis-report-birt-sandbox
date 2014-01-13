package com.funshion.artemis.common.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * http 工具
 * @author shenwf
 * Reviewed by
 */
public class HttpTools {

	/**
	 * 下载文件
	 * @param photoUrl
	 * @param fileName
	 * @return
	 */
	public static boolean saveUrlAs(String photoUrl, String fileName) {
		// 此方法只能用户HTTP协议
		try {
			URL url = new URL(photoUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			DataInputStream in = new DataInputStream(connection.getInputStream());
			DataOutputStream out = new DataOutputStream(new FileOutputStream(fileName));
			byte[] buffer = new byte[4096];
			int count = 0;
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
			out.close();
			in.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
