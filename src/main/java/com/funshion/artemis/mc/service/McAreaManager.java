package com.funshion.artemis.mc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.birt.report.engine.script.internal.ReportContextImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.funshion.artemis.dim.bean.DimArea;
import com.funshion.artemis.dim.dao.DimAreaDao;
import com.funshion.artemis.mc.dao.McAreaDao;

/**
 * 播控地域处理类
 * 
 * @author shenwf Reviewed by
 */
@Service
public class McAreaManager {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DimAreaDao dimAreaDao;
	@Autowired
	private McAreaDao mcAreaDao;

	/**
	 * 根据播控地域id 获取相关的地域名称
	 * 
	 * @param id
	 * @return
	 */
	public String getAreaName(String id) {
		DimArea area = null;
		if(id.length() > 6) {
		    area = getSpecialArea(id);
		} else {
			area =dimAreaDao.getAreaById(Long.parseLong(id));
		}
		if(area == null) {
			return "";
		}
		return area.getName();
	}
	
	/**
	 * 根据播控id 获取相关地域列表
	 * @param mcId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<DimArea> getAreaListByMcId(Long mcId) {
		List<Map> mcSnapShotList = new ArrayList<Map>();
		mcSnapShotList = (List<Map>) mcAreaDao.getStgAreaInfo(mcId);
		List<DimArea> areaList = handleAreaList(mcSnapShotList);
		areaList = getSpecialArea(areaList);
		return areaList;
	}
	
	/**
	 * 添加不分地域的信息
	 */
	public List<DimArea> getSpecialArea(List<DimArea> areaList) {
		Long id = -1l;
		String name = "全部地域";
		Integer areaLevel = -1;
	    Long parentId = -1L;
		DimArea dimArea = new DimArea(id, name, areaLevel, parentId);
		areaList.add(dimArea);
		return areaList;
	}
	
	/**
	 * 处理特殊地域
	 * @param mcSnapShotList
	 * @return
	 */
	public List<DimArea> handleAreaList(List<Map> mcSnapShotList) {
		List<DimArea> areaList = new ArrayList<DimArea>();
		for (Map map : mcSnapShotList) {
			Integer areaId = (Integer) map.get("area_id");

			if (areaId != null) {
				DimArea dimArea = null;
				if (areaId.toString().length() >= 7) {
					dimArea = getSpecialArea(areaId.toString());
				} else {
					dimArea = dimAreaDao.getAreaById(areaId.longValue());
				}

				if (dimArea != null) {
					areaList.add(dimArea);
				}
			}
		}
		return areaList;
	}
	
	/**
	 * 返回特殊地域对象
	 * @param id
	 * @return
	 */
	public DimArea getSpecialArea(String id) {
		String name = "";
		try {
			if (id == null || id.equals("")) {
				return null;
			}
            Long oldId = Long.parseLong(id);
            if(id.length() > 6) {
			  id = id.substring(1, id.length() - 5);
            }
			Long areaId = Long.parseLong(id);
			DimArea area = dimAreaDao.getAreaById(areaId);
			name = area.getName() + "其他";
			area.setId(oldId);
			area.setName(name);
			return area;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}
		return null;
	}
	
	/**
	 * 根据 地域id获取地域名字
	 * @param request
	 * @param areaId
	 * @return
	 */
	public String getAreaName(HttpServletRequest request, String areaId) {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()); 
		McAreaManager area = (McAreaManager) ctx.getBean("mcAreaManager");
		return area.getAreaName(areaId);
	}

	public void setDimAreaDao(DimAreaDao dimAreaDao) {
		this.dimAreaDao = dimAreaDao;
	}

	public void setMcAreaDao(McAreaDao mcAreaDao) {
		this.mcAreaDao = mcAreaDao;
	}
	
	
}
