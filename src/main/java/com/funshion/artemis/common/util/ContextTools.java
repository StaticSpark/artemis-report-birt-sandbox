package com.funshion.artemis.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

/**
 * web 上下文工具类
 * @author shenwf
 * Reviewed by zengyc
 */
public class ContextTools {
	/**
	 * 得到上下文中的内容 ，以map返回
	 * @param sc
	 * @return
	 */
	public static Map<String, String> getContextMap(ServletContext sc) {
		Map<String, String> map = new HashMap<String, String>();
		String configFilePath = sc.getRealPath("/WEB-INF") + File.separator + "classes" + File.separator + "jdbc-follow.properties";
		String workFolder = sc.getInitParameter("BIRT_VIEWER_WORKING_FOLDER");
		if ((File.separator).equals("\\") && workFolder.contains("/")) {
			workFolder = replaceStr(workFolder, "/", "\\");
		} else if ((File.separator).equals("/") && workFolder.contains("\\")) {
			workFolder = replaceStr(workFolder, "\\", "/");
		}

		workFolder = sc.getRealPath("/") + workFolder;
	
		map.put("configFilePath", configFilePath);
		map.put("workFolder", workFolder);
		
		Properties props = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(configFilePath);
			props.load(fis);;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
		}
		
		map.put("jdbc.url", props.getProperty("jdbc.url", ""));
		map.put("jdbc.username", props.getProperty("jdbc.username", ""));
		map.put("jdbc.password", props.getProperty("jdbc.password", ""));
		map.put("birt.home", props.getProperty("birt.home", ""));
		map.put("jndi.name", props.getProperty("jndi.name", ""));
		
		return map;
	}

	/**
	 * 将字符串中 分隔符 替换成自定分隔符
	 * 
	 * @param str
	 *            字符串
	 * @param split
	 *            原分隔符
	 * @param replace
	 *            现分隔符
	 * @return
	 */
	public static String replaceStr(String str, String split, String replace) {
		String result = "";
		if (str != null && !str.equals("")) {
			String[] arry = str.split(split);
			for (String s : arry) {
				result += s + replace;
			}
			result = result.substring(0, result.length() - replace.length());
		}
		return result;
	}
}
