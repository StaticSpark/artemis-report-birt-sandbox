package com.funshion.artemis.common.util;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.funshion.artemis.report.bean.ColumnInfo;

/**
 * 工具类
 * 
 * @author shenwf Reviewed by zengyc
 */
public class Tools {
	/**
	 * 检验该json是否 合法
	 * 
	 * @param json
	 * @return
	 */
	public static boolean checkJson(String json) {
		try {
			JSONArray jsonArrayMedCatInput = JSONArray.fromObject(json);
			List<ColumnInfo> columnList = (List<ColumnInfo>) JSONArray.toCollection(jsonArrayMedCatInput, ColumnInfo.class);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 格式化小时
	 * @param hour
	 * @return
	 */
	public static String formatHour(String hour) {
		int hourVal = Integer.parseInt(hour);
		if (hourVal < 9) {
			hour = "0" + hourVal;
			hour = hour + ":00~0" + (hourVal + 1) + ":00";
		} else if (hourVal == 9) {
			hour = "0" + hourVal + ":00~";
			hour = hour + (hourVal + 1) + ":00";
		} else {
			hour = hourVal + ":00~" + (hourVal + 1) + ":00";
		}
		return hour;
	}

	/**
	 * 得到2数相除的百分比
	 * 
	 * @param mol
	 *            分子
	 * @param den
	 *            分母
	 * @return
	 */
	public static String getPercent(double mol, double den) {
		if (den == 0) {
			return "0.00%";
		}

		if (den < 0) {
			return "-";
		}

		double percent = mol / den;
		percent = percent * 100;
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("0.00");
		return df.format(percent) + "%";
	}

	/**
	 * 得到2数相除的百分比
	 * 
	 * @param mol
	 *            分子
	 * @param den
	 *            分母
	 * @return
	 */
	public static String getPercent(String mol, String den) {
		if (mol != null && den != null & !mol.equals("") && !den.equals("")) {
			mol = mol.trim();
			den = den.trim();
			return getPercent(Double.parseDouble(mol), Double.parseDouble(den));
		} else {
			return "";
		}
	}

	/**
	 * 返回数据
	 * 
	 * @param request
	 * @param response
	 * @param data
	 */
	public static void transmitDataToJsp(HttpServletRequest request, HttpServletResponse response, String data) {
		try {
			request.setCharacterEncoding("utf8");
			response.setCharacterEncoding("utf8");
			response.getWriter().println(data);
		} catch (IOException e) {
			e.printStackTrace();
			try {
				response.getWriter().println("");
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取项目 class 文件根路径
	 * @return
	 */
	public static String getClassRoot() {
		String path = Tools.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		File file = new File(path);
		path = file.getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().getAbsolutePath() + File.separator;
		return path;
	}
	
	public static String getBirtFileRoot() {
		return getClassRoot() + "report" + File.separator + "birt" + File.separator;
	}

	/**
	 * 获取birt 文件根路径
	 * @param sc
	 * @return
	 */
	public static String getRptDesignFilePath(HttpServletRequest request) {
		String workFolder = "WEB-INF/classes/report/birt";
		if ((File.separator).equals("\\") && workFolder.contains("/")) {
			workFolder = replaceStr(workFolder, "/", "\\");
		} else if ((File.separator).equals("/") && workFolder.contains("\\")) {
			workFolder = replaceStr(workFolder, "\\", "/");
		}

		workFolder = request.getRealPath("/") + workFolder;
		return workFolder;
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
	
	public static void main(String[] args) {
		String strAsc = ascii2Native("\u8054\u901a\u5361");
		System.out.println(strAsc);
	}

	public static String ascii2Native(String str) {
		StringBuilder sb = new StringBuilder();
		int begin = 0;
		int index = str.indexOf("\\u");
		while (index != -1) {
			sb.append(str.substring(begin, index));
			sb.append(ascii2Char(str.substring(index, index + 6)));
			begin = index + 6;
			index = str.indexOf("\\u", begin);
		}
		sb.append(str.substring(begin));
		return sb.toString();
	}

	public static char ascii2Char(String str) {
		if (str.length() != 6) {
			throw new IllegalArgumentException("Ascii string of a native character must be 6 character.");
		}
		if (!"\\u".equals(str.substring(0, 2))) {
			throw new IllegalArgumentException("Ascii string of a native character must start with \\u.");
		}
		String tmp = str.substring(2, 4);
		int code = Integer.parseInt(tmp, 16) << 8;
		tmp = str.substring(4, 6);
		code += Integer.parseInt(tmp, 16);
		return (char) code;
	}

}
