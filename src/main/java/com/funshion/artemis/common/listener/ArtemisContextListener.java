package com.funshion.artemis.common.listener;

import org.springframework.beans.factory.annotation.Autowired;
import com.funshion.artemis.custom.service.AdpManager;

/**
 * 项目启动类
 * 
 * @author shenwf Reviewed by
 */
public class ArtemisContextListener {
	@Autowired
	private AdpManager adpManager;

	public void createReport() {
		//System.out.println(adpManager.getReadyAdpBase());
	}
}
