package com.funshion.artemis.custom.bean;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 广告基础库实体
 * 与数据表ad_base对应
 * @author guanzx
 *
 */
public class AdBase {
	
	private Long id;                // 请求号
	private String code;			// 代码
	private String name;			// 名称
	private Long tplId;				// 广告形式ID 
	private Long  fmtType;			// 广告形式 格式类型
	private Long paralistId;		// 广告形式参数列表ID（目前默认和广告ID一致）
	private Long catId;				//题材id
	private Long ordId;             //订单Id
	private Integer  adStatus;      //广告状态
	private Long adProcess;         //广告过程
	private Timestamp logTime;			// 更新时间
	private String clkUrl;			// 点击URL
	private Integer priority,adPriority;// 优先级
	private Integer weight,adWeight;	// 权重
	private Date pdateFrom;			// 开始时间
	private Date pdateEnd;			// 结束时间
	private Integer nnMinute;		//并发时间间隔
	private Long nnVv;				//每分钟vv
	private Integer isdelete=0;		//是否删除标识，1标识已删除
	private Date createTime; //创建时间
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the tplId
	 */
	public Long getTplId() {
		return tplId;
	}
	/**
	 * @param tplId the tplId to set
	 */
	public void setTplId(Long tplId) {
		this.tplId = tplId;
	}
	/**
	 * @return the fmtType
	 */
	public Long getFmtType() {
		return fmtType;
	}
	/**
	 * @param fmtType the fmtType to set
	 */
	public void setFmtType(Long fmtType) {
		this.fmtType = fmtType;
	}
	/**
	 * @return the paralistId
	 */
	public Long getParalistId() {
		return paralistId;
	}
	/**
	 * @param paralistId the paralistId to set
	 */
	public void setParalistId(Long paralistId) {
		this.paralistId = paralistId;
	}
	/**
	 * @return the catId
	 */
	public Long getCatId() {
		return catId;
	}
	/**
	 * @param catId the catId to set
	 */
	public void setCatId(Long catId) {
		this.catId = catId;
	}
	/**
	 * @return the ordId
	 */
	public Long getOrdId() {
		return ordId;
	}
	/**
	 * @param ordId the ordId to set
	 */
	public void setOrdId(Long ordId) {
		this.ordId = ordId;
	}
	/**
	 * @return the adStatus
	 */
	public Integer getAdStatus() {
		return adStatus;
	}
	/**
	 * @param adStatus the adStatus to set
	 */
	public void setAdStatus(Integer adStatus) {
		this.adStatus = adStatus;
	}
	/**
	 * @return the adProcess
	 */
	public Long getAdProcess() {
		return adProcess;
	}
	/**
	 * @param adProcess the adProcess to set
	 */
	public void setAdProcess(Long adProcess) {
		this.adProcess = adProcess;
	}
	/**
	 * @return the logTime
	 */
	public Timestamp getLogTime() {
		return logTime;
	}
	/**
	 * @param logTime the logTime to set
	 */
	public void setLogTime(Timestamp logTime) {
		this.logTime = logTime;
	}
	/**
	 * @return the clkUrl
	 */
	public String getClkUrl() {
		return clkUrl;
	}
	/**
	 * @param clkUrl the clkUrl to set
	 */
	public void setClkUrl(String clkUrl) {
		this.clkUrl = clkUrl;
	}
	/**
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	/**
	 * @return the adPriority
	 */
	public Integer getAdPriority() {
		return adPriority;
	}
	/**
	 * @param adPriority the adPriority to set
	 */
	public void setAdPriority(Integer adPriority) {
		this.adPriority = adPriority;
	}
	/**
	 * @return the weight
	 */
	public Integer getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	/**
	 * @return the adWeight
	 */
	public Integer getAdWeight() {
		return adWeight;
	}
	/**
	 * @param adWeight the adWeight to set
	 */
	public void setAdWeight(Integer adWeight) {
		this.adWeight = adWeight;
	}
	/**
	 * @return the pdateFrom
	 */
	public Date getPdateFrom() {
		return pdateFrom;
	}
	/**
	 * @param pdateFrom the pdateFrom to set
	 */
	public void setPdateFrom(Date pdateFrom) {
		this.pdateFrom = pdateFrom;
	}
	/**
	 * @return the pdateEnd
	 */
	public Date getPdateEnd() {
		return pdateEnd;
	}
	/**
	 * @param pdateEnd the pdateEnd to set
	 */
	public void setPdateEnd(Date pdateEnd) {
		this.pdateEnd = pdateEnd;
	}
	/**
	 * @return the nnMinute
	 */
	public Integer getNnMinute() {
		return nnMinute;
	}
	/**
	 * @param nnMinute the nnMinute to set
	 */
	public void setNnMinute(Integer nnMinute) {
		this.nnMinute = nnMinute;
	}
	/**
	 * @return the nnVv
	 */
	public Long getNnVv() {
		return nnVv;
	}
	/**
	 * @param nnVv the nnVv to set
	 */
	public void setNnVv(Long nnVv) {
		this.nnVv = nnVv;
	}
	/**
	 * @return the isdelete
	 */
	public Integer getIsdelete() {
		return isdelete;
	}
	/**
	 * @param isdelete the isdelete to set
	 */
	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
		
}
