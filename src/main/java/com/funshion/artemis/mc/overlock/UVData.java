package com.funshion.artemis.mc.overlock;

import java.util.ArrayList;
import java.util.List;

/**
 * 存放uv数据的列表类
 * @author guanzx
 *
 */
public class UVData {
	
	private List<PeriodUV> periodUvList = new ArrayList<PeriodUV>();

	public List<PeriodUV> getPeriodUvList() {
		return periodUvList;
	}

	public void setPeriodUvList(List<PeriodUV> periodUvList) {
		this.periodUvList = periodUvList;
	}
	
}
