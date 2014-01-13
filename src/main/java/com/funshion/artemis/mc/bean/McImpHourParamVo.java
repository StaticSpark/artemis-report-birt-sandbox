package com.funshion.artemis.mc.bean;

/**
 * 播控imp小时报告参数类
 * @author shenwf
 * Reviewed by
 */
public class McImpHourParamVo {
	/**
	 * path
	 */
	private String path;
	/**
	 * 点击率限制
	 */
	private String clkCtrl;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getClkCtrl() {
		return clkCtrl;
	}

	public void setClkCtrl(String clkCtrl) {
		this.clkCtrl = clkCtrl;
	}

}
