package com.funshion.artemis.autotest.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 老版本excel(xml格式)解析
 * 
 * @author shenwf Reviewed by
 */
public class OldExcelParse {
	/**
	 * 解析老版本excel
	 * 
	 * @param fileName
	 * @return
	 * @throws DocumentException
	 */
	public static List<String> parseExcel(String fileName) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(fileName));
		Element rootElm = document.getRootElement();
		Element worksheet = rootElm.element("Worksheet");
		List rows = worksheet.element("Table").elements("Row");
		List<String> list = new ArrayList<String>();
		for (Object or : rows) {
			Element row = (Element) or;
			List cells = row.elements("Cell");
			String str = "";
			for (Object oc : cells) {
				Element cell = (Element) oc;
				Element data = cell.element("Data");
				String value = data == null ? "-" : data.getText();
				value = value == null ? "-" : value;
				value = value.equals("") ? "-" : value;
				if (value.contains("T") && value.contains(".000")) {
					value = value.replaceAll("T", " ");
					value = value.replaceAll(".000", " ");
					value = value.trim();
				}
				str += value + ",";
			}
			if (str.length() > 0) {
				list.add(str.substring(0, str.length() - 1) + "\n");
			}
		}
		list.remove(0);
		return list;
	}
}
