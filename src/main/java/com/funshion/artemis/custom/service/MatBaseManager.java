package com.funshion.artemis.custom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funshion.artemis.custom.bean.MatBase;
import com.funshion.artemis.custom.dao.MatBaseDao;

/**
 * 物料处理类
 * @author guanzx
 *
 */
@Service
public class MatBaseManager {
	@Autowired
	private MatBaseDao matBaseDao;
	
	public List<MatBase> getAllMat() {		
		List<MatBase> matList = matBaseDao.getAll();
		return matList;
	}
	
	/**
	 * 得到物料查询条件列表
	 * @author zhaojj
	 */
	public String getMatCondition(Map<String, String[]> paramMap) {
		String[] iDisplayStart = paramMap.get("iDisplayStart");
		String[] iDisplayLength = paramMap.get("iDisplayLength");
		String[] sEcho = paramMap.get("sEcho");
		String[] searchs = paramMap.get("sSearch");
		String start = iDisplayStart[0];
		String length = iDisplayLength[0];
		String search = searchs[0];
		List<Map> list = matBaseDao.getAllMatConditionBeanList(start, length, search);
		StringBuffer sb = new StringBuffer();
		for (Map map : list) {
			sb.append("[");
			String name = map.get("name").toString();
			Object id =  map.get("id");
			String type = map.get("type") == null ? "" : map.get("type").toString();
			String width = map.get("width") == null ? "" : map.get("width").toString();
			String height = map.get("height") == null ? "" : map.get("height").toString();
			String playDur = map.get("playDur") == null ? "" : map.get("playDur").toString();
			
			sb.append("\"<input type='checkbox' mark='mat-c' id='mat-c-" + id + "' name='con-check'></input>\",");
			sb.append("\"<span style='float: left'><font size=2>" + id + "</font></span>\",");
			sb.append("\"<span style='float: left'><font size=2>" + name.replaceAll("\"", "") + "</font></span>\",");
			sb.append("\"<span style='float: left'><font size=2>" + type.replaceAll("\"", "") + "</font></span>\",");
			sb.append("\"<span style='float: left'><font size=2>" + width.replaceAll("\"", "") + "*" + height.replaceAll("\"", "") + "</font></span>\",");
			sb.append("\"<span style='float: left'><font size=2>" + playDur.replaceAll("\"", "") + "</font></span>\",");
			sb.append("\"<span style='float: left'><img src='/artemis/images/report/list_view.gif' alt='' /></span>\"");
			sb.append("],");
		}
		if(sb.length() > 1) {
		  sb.deleteCharAt(sb.length() - 1);
		}
		Long count = matBaseDao.getMatCount(search);
		String message = "{\"sEcho\":" + sEcho[0] + ",\"iTotalRecords\":\" " + count + "\",\"iTotalDisplayRecords\":\"" + count + "\",\"aaData\":["
				+ sb.toString() + "]}";
		return message;
	}
		
	/**
	 * 物料全选
	 */
	public List<MatBase> getMatAllCheckList(String param){
		List<Map> list = matBaseDao.getMatAllCheckList(param);
		List<MatBase> matBaseList = new ArrayList<MatBase>();
		for (Map map:list) {
			String name = map.get("name").toString();
			Object id =  map.get("id");
			String type = map.get("type") == null ? "" : map.get("type").toString();
			String width = map.get("width") == null ? "" : map.get("width").toString();
			String height = map.get("height") == null ? "" : map.get("height").toString();
			String playDur = map.get("playDur") == null ? "" : map.get("playDur").toString();
			
			MatBase matBase = new MatBase();
			matBase.setName(name);
			matBase.setId(Long.parseLong(id.toString()));
			matBase.setType(type);
			matBase.setWidth(Integer.parseInt(width));
			matBase.setHeight(Integer.parseInt(height));
			matBase.setPlayDur(Integer.parseInt(playDur));
			matBaseList.add(matBase);		
		}
		
		return matBaseList;
	}
}
