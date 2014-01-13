package com.funshion.artemis.dim.service;

import java.util.List;

import com.funshion.artemis.dim.bean.DimArea;

/**
 * The interface for class com.funshion.base.dim.service.DimAreaManager.
 * 
 * @author jiangxu
 * Reviewed-by weibl gongpb
 */
public interface IDimAreaManager {

	/**
	 * 国家
	 */
	int COUNTRY = 1;
	/**
	 * 省份
	 */
	int PROVINCE = 2;
	/**
	 * 市
	 */
	int CITY = 3;
	
	/**
	 * 根据ID获得DimArea实例
	 */
	DimArea getDimAreaById(Long id);

	/**
	 * 获取地域定向json字符串 格式： { 'country':[ { 'name':,//国家名 'id': 'province':[ {
	 * 'name'://省份名 'id': 'city':[ { 'id': 'name'://城市名 } ] } ] } ] }
	 * 
	 */
	String getAllArea();

	/**
	 * 得到广告的地域定向信息
	 * 
	 */
	String getSelectedArea(Long adId);

	/**
	 * 得到下一级地域名
	 */
	String getSubArea(Long id);

	/**
	 * 得到所有国家名
	 */
	String getAllContry();
	
	List<DimArea> getAreaByLevel(Integer level);
	
	/**
	 * 根据 区域Id得到对应描述，例如:
	 * <li>areaId对应中国得到     =>  中国.*.*</li>
	 * <li>areaId对应北京得到     =>  中国.北京.*</li>
	 * <li>areaId对应广州得到     =>  中国.广东.广州</li>
	 */
	String getAreaDesc(Long areaId);
}
