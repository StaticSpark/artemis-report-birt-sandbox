package com.funshion.artemis.dim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funshion.artemis.dim.bean.DimArea;
import com.funshion.artemis.dim.dao.DimAreaDao;

@Service
public class DimAreaManager implements IDimAreaManager {
	@Autowired
	private DimAreaDao dimAreaDao;

	/**
	 * 获取所有地域信息,地域定向json字符串 格式： { 'country':[ { 'name':,//国家名 'id': 'province':[
	 * { 'name'://省份名 'id': 'city':[ { 'id': 'name'://城市名 } ] } ] } ] }
	 * 
	 * @return
	 */
	public String getAllArea() {
		List<DimArea> allCountryList = dimAreaDao.getByLevel(COUNTRY);
		List<DimArea> allProvinceList = dimAreaDao.getByLevel(PROVINCE);
		List<DimArea> allCityList = dimAreaDao.getByLevel(CITY);
		List<DimArea> otherCountrys = dimAreaDao.getOtherCountrys();
		return getAreaJson(allCountryList, allProvinceList, allCityList, otherCountrys);
	}

	/**
	 * 得到地域json信息
	 * 
	 * @param countryList
	 * @param provinceList
	 * @param cityList
	 * @param otherCountrys
	 * @return
	 */
	private String getAreaJson(List<DimArea> countryList, List<DimArea> provinceList, List<DimArea> cityList, List<DimArea> otherCountrys) {
		String json = "{\"country\":[";
		int citySize = cityList.size();

		for (DimArea country : countryList) {// 遍历 组成json字符串
			json += "{\"name\":\"" + country.getName() + "\",";
			json += "\"id\":\"" + country.getId() + "\",";
			json += "\"province\":[";
			int i = 0;
			for (DimArea province : provinceList) {
				if ((long) province.getParentId() == (long) country.getId()) {
					json += "{\"name\":\"" + province.getName() + "\",";
					json += "\"id\":\"" + province.getId() + "\",";
					json += "\"city\":[";
					int j = 0;
					for (DimArea city : cityList) {
						if ((long) city.getParentId() == (long) province.getId()) {
							json += "{\"name\":\"" + city.getName() + "\", " + "\"id\":\"" + city.getId() + "\"},";
						}

						if (j == citySize - 1 && json.endsWith(",")) {
							json = json.substring(0, json.length() - 1);
						}
						j++;
					}
					json += "]},";
					i++;
				}
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json += "]},";
		}

		json = json.substring(0, json.length() - 1);
		json += "]}";
		return json;
	}

	/**
	 * 将地域中的省份id转换成 城市id
	 * @param areaIds
	 * @return
	 */
	public String changeProviceIdToCityId(String areaIds) {
		if (areaIds == null || areaIds.equals("")) {
			return "";
		}
		String[] ids = areaIds.split(",");
		for (String id : ids) {
			Long areaId = Long.parseLong(id);
			DimArea area = dimAreaDao.getAreaById(areaId);
			if (area!= null && area.getAreaLevel() == 2 && area.getParentId() == 1) {
				List list = dimAreaDao.getByParentId(area.getId());
				for(Object o : list) {
					DimArea subArea = (DimArea) o;
					areaIds += "," + subArea.getId();
				}
			}
		}
		return areaIds;
	}

	@Override
	public DimArea getDimAreaById(Long id) {
		DimArea dimArea = dimAreaDao.getAreaById(id);
		return dimArea;
	}

	@Override
	public String getSubArea(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAllContry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DimArea> getAreaByLevel(Integer level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAreaDesc(Long areaId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSelectedArea(Long adId) {
		// TODO Auto-generated method stub
		return null;
	}


	public void setDimAreaDao(DimAreaDao dimAreaDao) {
		this.dimAreaDao = dimAreaDao;
	}
	
	

}
