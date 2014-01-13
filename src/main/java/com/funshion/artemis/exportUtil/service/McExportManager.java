package com.funshion.artemis.exportUtil.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funshion.artemis.common.util.ReportXmlReader;
import com.funshion.artemis.custom.service.AdReportSqlManager;
import com.funshion.artemis.exportUtil.bean.ExportColumnBean;
import com.funshion.artemis.exportUtil.dao.McExportDao;

/**
 * 
 * @author guanzx
 *
 */
@Service
public class McExportManager {
	
	@Autowired
	private McExportDao mcExportDao; 
			
	public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws IOException {     	
		
		String json = request.getParameter("json");	
		JSONObject jsonObject = JSONObject.fromObject(json);
		
		String fileName = "播控-用户小时报告";
		fileName = getExportName(fileName)+"("+getCurrentDate()+")";
		
		OutputStream out = response.getOutputStream();
		response.setContentType("application/vnd.ms-excel;charset=UTF-8"); 
		response.addHeader("Content-Disposition","attachment;filename="+fileName+".xls");
		
		Workbook wb = new HSSFWorkbook();		
		Iterator iter = jsonObject.keys();
		while(iter.hasNext()) {
			String key = (String)iter.next();
			String column[] = key.split(",");
			String monId = column[0];
			String areaId = column[1];
			String monDate = column[2];
			String areaName = column[3];
			String isVisualArea = column[4];
			String path = column[5];
			String mcName = column[6];
			String impLimit = column[7];
			List<ExportColumnBean> list = mcExportDao.getReportInfoByParams(monId,areaId,monDate,isVisualArea,path);		
			String sheetTitle = getSheetTitle(areaName, monDate, path);
			List titleList = McExportManager.generateTitle(mcName,monDate,areaName,impLimit,path);				
			String[] headers = {"时段","执行投放量","地域播放量","播放比","执行点击量","地域点击量","点击率","播放请求量","无效播放量","异常播放量","点击请求量","无效点击量","异常点击量"};		
			McExportManager.createExcel(wb,sheetTitle,headers,titleList,list,path);			
		}
		wb.write(out);
		out.close();
	}
	
	/**
	 * 获得sheet的名称
	 * @param areaName
	 * @param monDate
	 * @param path
	 * @return
	 */
	public static String getSheetTitle(String areaName,String monDate,String path) {
		String sheetTitle = null;
		sheetTitle = areaName+"("+monDate+")"+"{"+path+"}";
		if (path.equals("*")) {
			sheetTitle = areaName+"("+monDate+")";
		}else if(path.equals("0")) {
			path = "-";
			sheetTitle = areaName+"("+monDate+")"+"{"+path+"}";
		}
		return sheetTitle;
	}
	
    public static void createExcel(Workbook wb,String sheetTitle,String[] headers,List titleList,List<ExportColumnBean> list,String path) {	
		Sheet sheet = wb.createSheet(sheetTitle);
		sheet.setDefaultColumnWidth(10);
		//生成样式
		HSSFCellStyle style = (HSSFCellStyle) wb.createCellStyle();
		HSSFCellStyle percentstyle = (HSSFCellStyle) wb.createCellStyle();
		HSSFCellStyle headerStyle = (HSSFCellStyle) wb.createCellStyle();
		percentstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//生成字体
		HSSFFont font = (HSSFFont) wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		Row row = null;
		//表头文件
		for(int i = 0;i<titleList.size();i++) {
			row = sheet.createRow(i);
			row.createCell(0).setCellValue(titleList.get(i).toString());
			sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 12));
		}		
		row = sheet.createRow(titleList.size());
		for(int i = 0;i<headers.length;i++) {
			Cell cell= row.createCell(i);
			headerStyle.setFont(font);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(headers[i]);
		}
						
		Map<Integer,List<Long>> map = new HashMap<Integer,List<Long>>();
		for (int i = 0;i<list.size();i++) {
			ExportColumnBean ecb = list.get(i);			
			row = sheet.createRow(i+1+titleList.size());				
			McExportManager.setHourStyle(0,ecb.getMonHour(),row,style);
			McExportManager.setStyle(1,ecb.getImpFwd(),row,style,map);			
			McExportManager.setStyle(2,ecb.getImpChokeArea(),row,style,map);		
			McExportManager.setImpPercentStyle(3,ecb.getImpPercent(),row,percentstyle,path);
			McExportManager.setStyle(4,ecb.getClkFwd(),row,style,map);
			McExportManager.setStyle(5,ecb.getClkChokeArea(),row,style,map);
			McExportManager.setClkPercentStyle(6,ecb.getClkFwd(),ecb.getImpFwd(),row,percentstyle);	
			McExportManager.setStyle(7,ecb.getActImp(),row,style,map);
			McExportManager.setStyle(8,ecb.getImpBlock(),row,style,map);
			McExportManager.setStyle(9,ecb.getImpChoke(),row,style,map);
			McExportManager.setStyle(10,ecb.getActClk(),row,style,map);
			McExportManager.setStyle(11,ecb.getClkBlock(),row,style,map);
			McExportManager.setStyle(12,ecb.getClkChoke(),row,style,map);	
		}
		//求和
		row = sheet.createRow(titleList.size()+list.size()+1);
		Cell cell= row.createCell(0);
		cell.setCellStyle(style);
		cell.setCellValue("总计");
		Long clkFwdSum = 0L;
		Long impFwdSum = 0L;
		for (int i:map.keySet()) {
			List<Long> list1 = map.get(i);
			Long sum = 0L;
			for (int j = 0;j<list1.size();j++) {
				sum = sum + list1.get(j);
			}
			
			if (i == 4) {
				clkFwdSum = sum;
			}
			if (i == 1) {
				impFwdSum = sum;
			}
			cell= row.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(sum);
		}
		
		if (impFwdSum == 0L) {
			cell= row.createCell(6);
			cell.setCellStyle(percentstyle);
			cell.setCellValue("-");
		}else {
			cell= row.createCell(6);
			setPercentStyle(clkFwdSum, impFwdSum, cell, percentstyle);
		}
	}
	
    /**
     * 导出报表的表头文件
     * @param mcName
     * @param monDate
     * @param areaName
     * @param impLimit
     * @param path
     * @return
     */
	public static List generateTitle(String mcName,String monDate,String areaName,String impLimit,String path) {
		String fileName = "Microlens.funshion.com" +" 播控-用户小时报告";
		String exportDate = getCurrentDate();
		String rptType = "播控报表";
		List list = new ArrayList();
		list.add(fileName);
		list.add("导出报表日期:"+exportDate);
		list.add("报表类型:"+rptType);
		list.add("播控名称:"+mcName);
		list.add("报表日期:"+monDate);
		list.add("地域:"+areaName);
		list.add("设定投放量:"+impLimit);
		if (path.equals("0")) {
			path = "-";
		}
		list.add("path:"+path);
		return list;	
	}
	
	//导出报告文件名中文化
	public static String getExportName(String fileName) {
		String mcPrefix = "Microlens.funshion.com";
		for (int i =0;i<fileName.length();i++) {
			char c = fileName.charAt(i);
			
			if (c < 0x00 || c >= 0x80) {
				try {
					fileName = new String(fileName.getBytes(Charset.defaultCharset()),"iso-8859-1");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		return mcPrefix+fileName;
	}
	
	public static String getCurrentDate() {		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String currentDate = formatter.format(date);
		return currentDate;
	}
	
	public static void setStyle(int i,Long field,Row row,HSSFCellStyle style,Map<Integer,List<Long>> map) {	
		if (!map.containsKey(i)) {
			int key = i;
			map.put(key, new ArrayList<Long>());
			map.get(key).add(field);
		}else {
			map.get(i).add(field);
		}	
		Cell cell = row.createCell(i);
		cell.setCellStyle(style);
		cell.setCellValue(field);
	}
	public static void setHourStyle(int i,int field,Row row,HSSFCellStyle style) {
		Cell cell = row.createCell(i);
		cell.setCellStyle(style);
		String hourVal = null;
		if(field < 9) {
			hourVal = "0" + String.valueOf(field);
			hourVal = hourVal + ":00~0" + String.valueOf(field + 1) + ":00";
		} else if(field == 9)  {
			hourVal = "0" + String.valueOf(field) + ":00~";
			hourVal = hourVal + String.valueOf(field + 1) + ":00";
		} else {
			hourVal = String.valueOf(field) + ":00~" + String.valueOf(field + 1) + ":00";
		}
		cell.setCellValue(hourVal);
	}
	
	public static void setClkPercentStyle(int i,Long firstField,Long secondField,Row row,HSSFCellStyle style) {
		Cell cell = row.createCell(i);		
		if (secondField != 0) {
			setPercentStyle(firstField, secondField, cell, style);
		}else {
			cell.setCellStyle(style);
			cell.setCellValue("-");
		}		
	}
	
	public static void setPercentStyle(Long firstField,long secondField,Cell cell ,HSSFCellStyle style) {
		float percent = (float)firstField/secondField;			
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
		cell.setCellStyle(style);
		cell.setCellValue(percent);
	}
	
	public static void setImpPercentStyle(int i,String field,Row row,HSSFCellStyle style,String path) {
		Cell cell = row.createCell(i);
		if (path.equals("*")) {
			String percent = "-";
			cell.setCellStyle(style);
			cell.setCellValue(percent);
		}else {		
			if (!field.contains(",")) {
				Double percent = 1D;
				style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
				cell.setCellStyle(style);
				cell.setCellValue(percent);
			}else {
				float percent = getMinDivMax(field);
				if (percent != -1.0) {
					style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
					cell.setCellStyle(style);
					cell.setCellValue(percent);
				}else {
					cell.setCellStyle(style);
					cell.setCellValue("-");
				}
				
			}
		}
		
	}
	
	public static float getMinDivMax(String field) {
		String[] column = field.split(",");
		List<Long> list = new ArrayList<Long>();
		for (int i=0;i<column.length;i++) {
			list.add(Long.parseLong(column[i]));
		}
		Long max = list.get(0);
		Long min = list.get(0);
		for (Long i:list) {
			max = max>i?max:i;
			min = min<i?min:i;
		}
		if (max != 0) {
			float percent = (float) (min/max);
			return percent;
		}else{
			return -1;//当max为0时，此时默认返回-1
		}
	}
	
	public static String getRptSql(String conditions) {
		String sql = ReportXmlReader.getReportSqlByName("imp_hour_report").getSql();
		Map map = new HashMap();
		map.put("condition", conditions);
		sql = AdReportSqlManager.sqlParam(sql, map);
		return sql;
	}
}
