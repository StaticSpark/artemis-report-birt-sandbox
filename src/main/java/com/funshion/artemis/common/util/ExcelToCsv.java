package com.funshion.artemis.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelToCsv {
	/**
	 * 将list容器中的数据写入csv文件
	 * 
	 * @param dataList
	 * @param outPutFileName
	 * @throws IOException
	 */
	public void writeDataToCsv(List<String> dataList, String outPutFileName) throws IOException {
	    File file = new File(outPutFileName);
	    if(!file.exists()) {
	    	file.createNewFile();
	    }
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		for (String line : dataList) {
			bw.write(line + "\n");
		}
		bw.close();
	}

	/**
	 * 将excel转换成csv文件
	 * @param sourceXlsFileName 原excel文件
	 * @param outPutFileName 
	 * @throws IOException
	 */
	public void changeExcelToCsv(String sourceXlsFileName, String outPutFileName) throws IOException {
		List<String> dataList = readExcelContent(sourceXlsFileName);
		writeDataToCsv(dataList, outPutFileName);
	}

	/**
	 * 读取Excel数据内容
	 * 
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 * @throws IOException 
	 */
	public List<String> readExcelContent(String fileName) throws IOException {
		InputStream is = new FileInputStream(fileName);
		List<String> list = new ArrayList();
		String str = "";
		POIFSFileSystem fs = new POIFSFileSystem(is);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		HSSFRow row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			int j = 0;
			while (j < colNum) {
				String cellVal = getCellFormatValue(row.getCell((short) j)).trim();
				cellVal = cellVal.equals("") ? "-" : cellVal;
				str += cellVal + ",";
				j++;
			}
			list.add(str);
			str = "";
		}
		return list;
	}
	

	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(HSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC:
			case HSSFCell.CELL_TYPE_FORMULA: {
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);

				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			// 如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}

	public static void main(String[] args) throws IOException {
		ExcelToCsv excelToCsv = new ExcelToCsv();
		excelToCsv.changeExcelToCsv("d:\\BI\\ad_report(14).xls", "d:\\BI\\test1.csv");
	}
}
