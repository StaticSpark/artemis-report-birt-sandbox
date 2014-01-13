package com.funshion.artemis.custom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funshion.artemis.custom.bean.AdpBase;
import com.funshion.artemis.custom.bean.DimAdpSize;
import com.funshion.artemis.custom.dao.AdpBaseDao;
import com.funshion.artemis.custom.dao.DimAdpSizeDao;


/**
 * 广告位处理类
 * @author guanzx
 *
 */
@Service
public class AdpManager {
	
	@Autowired 
	private AdpBaseDao adpBaseDao;
	@Autowired
	private DimAdpSizeDao dimAdpSizeDao;
	private static final String readyStatus = "3";
	
	/**
	 * 获取已就绪广告位列表
	 * @param status
	 * @return
	 */
	public List<AdpBase> getReadyAdpBase() {
		
		List<AdpBase> adpList = adpBaseDao.getAdpList(readyStatus);
		for(AdpBase adpBase : adpList) {
			if(adpBase.getSize() != null) {
				Long size = adpBase.getSize().longValue();			
				DimAdpSize dimAdpSize = dimAdpSizeDao.get(size);
				if(dimAdpSize != null) {
					adpBase.setDimAdpSize(dimAdpSize);	//转换为广告位列表的名称 通过查询另外的数据
					adpBase.setAdpSize(dimAdpSize.getName());
				}					
			}
		}
		return adpList;
	}
	
	/*
	 * 根据web传来的参数得到已经就绪的广告位
	 */
	public List<AdpBase> getPreciseReadyAdpBase(String param) {
		
		List<AdpBase> adpList = adpBaseDao.getPreciseAdpList(param,readyStatus);
		for(AdpBase adpBase : adpList) {
			if(adpBase.getSize() != null) {
				Long size = adpBase.getSize().longValue();			
				DimAdpSize dimAdpSize = dimAdpSizeDao.get(size);
				if(dimAdpSize != null) {
					adpBase.setDimAdpSize(dimAdpSize);	//转换为广告位列表的名称 通过查询另外的数据
					adpBase.setAdpSize(dimAdpSize.getName());
				}					
			}
		}
		return adpList;
	}	
}
