package com.funshion.artemis.exportUtil.bean;

/**
 * 该类与sql查询出来的字段保持一致
 * 
 * 导出报表字段
 * impFwd:执行投放量;impChokeArea:地域播放量;impPercent:播放比;
 * clkFwd:执行点击量;clkChokeArea:地域点击量;clkRate:点击率
 * actImp:播放请求量;impBlock:无效播放量;impChoke:异常播放量
 * actClk:点击请求量;clkBlock:无效点击量;clkChoke:异常点击量
 * @author guanzx
 *
 */
public class ExportColumnBean {

	private String groupMark;
	private String monId;
	private String monDate;
	private String areaId;
	private int monHour;
	private String areaName;
	private int path;
	private Long impFwd;
	private Long impChokeArea;
	private String impPercent;
	private Long clkFwd;
	private Long clkChokeArea;
	private String clkRate;
	private Long actImp;
	private Long impBlock;	
	private Long actClk;
	private Long clkBlock;
	private Long impChoke;
	private Long clkChoke;

	
	public String getGroupMark() {
		return groupMark;
	}
	public void setGroupMark(String groupMark) {
		this.groupMark = groupMark;
	}
	public String getMonId() {
		return monId;
	}
	public void setMonId(String monId) {
		this.monId = monId;
	}
	public String getMonDate() {
		return monDate;
	}
	public void setMonDate(String monDate) {
		this.monDate = monDate;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public int getMonHour() {
		return monHour;
	}
	public void setMonHour(int monHour) {
		this.monHour = monHour;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public int getPath() {
		return path;
	}
	public void setPath(int path) {
		this.path = path;
	}
	public Long getImpFwd() {
		return impFwd;
	}
	public void setImpFwd(Long impFwd) {
		this.impFwd = impFwd;
	}
	public Long getImpChokeArea() {
		return impChokeArea;
	}
	public void setImpChokeArea(Long impChokeArea) {
		this.impChokeArea = impChokeArea;
	}
	public String getImpPercent() {
		return impPercent;
	}
	public void setImpPercent(String impPercent) {
		this.impPercent = impPercent;
	}
	public Long getClkFwd() {
		return clkFwd;
	}
	public void setClkFwd(Long clkFwd) {
		this.clkFwd = clkFwd;
	}
	public Long getClkChokeArea() {
		return clkChokeArea;
	}
	public void setClkChokeArea(Long clkChokeArea) {
		this.clkChokeArea = clkChokeArea;
	}
	public String getClkRate() {
		return clkRate;
	}
	public void setClkRate(String clkRate) {
		this.clkRate = clkRate;
	}
	public Long getActImp() {
		return actImp;
	}
	public void setActImp(Long actImp) {
		this.actImp = actImp;
	}
	public Long getImpBlock() {
		return impBlock;
	}
	public void setImpBlock(Long impBlock) {
		this.impBlock = impBlock;
	}

	public Long getActClk() {
		return actClk;
	}
	public void setActClk(Long actClk) {
		this.actClk = actClk;
	}
	public Long getClkBlock() {
		return clkBlock;
	}
	public void setClkBlock(Long clkBlock) {
		this.clkBlock = clkBlock;
	}	
	public Long getImpChoke() {
		return impChoke;
	}
	public void setImpChoke(Long impChoke) {
		this.impChoke = impChoke;
	}
	public Long getClkChoke() {
		return clkChoke;
	}
	public void setClkChoke(Long clkChoke) {
		this.clkChoke = clkChoke;
	}
	
	@Override
	public String toString() {
		return "ExportColumn [groupMark="+groupMark+",monId="+monId+",monDate="+monDate+"," +
				"areaId="+areaId+", monHour="+monHour+",areaName="+areaName+", path="+path+"," +
						"impFwd="+impFwd+", impChokeArea="+impChokeArea+", impPercent="+impPercent+"," +
								" clkFwd="+clkFwd+", clkChokeArea="+clkChokeArea+", clkRate="+clkRate+"," +
										" actImp="+actImp+", impBlock="+impBlock+"," +
												" actClk="+actClk+", clkBlock="+clkBlock+",impChoke="+impChoke+",clkChoke="+clkChoke+" ]";
	} 	
}
