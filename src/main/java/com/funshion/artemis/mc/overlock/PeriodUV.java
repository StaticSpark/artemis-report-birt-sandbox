package com.funshion.artemis.mc.overlock;

import java.util.Map;

import com.ibm.icu.math.BigDecimal;

/**
 * 周期UV数据类
 * @author guanzx
 *
 */
public class PeriodUV {
	private String areaName;
	private String areaId;
	private String monDate;
	private String uvLimit;
	private Double uv1;
	private Double uv2;
	private Double uv3;
	private Double uv4;
	private Double uv5;
	private Double uv6;
	private Double uv7;
	private Double uv8;
	private Double uv9;
	private Double uv10;
		
	
	public void setFieldValue(Map<String,Object> map,String[] column) {
	
		if (map.get("area_name").toString() != null ) {
			this.areaName = map.get("area_name").toString();
		}
		
		if (map.get("area_id").toString() != null ) {
			this.areaId = map.get("area_id").toString();
		}
		
		if (map.get("mon_date").toString() != null ) {
			this.monDate = map.get("mon_date").toString();
		}
		
		if (map.get("uv_limit").toString() != null ){
			this.uvLimit = map.get("uv_limit").toString();
		}else {
			this.uvLimit = "-";
		}	
		this.uv1 = getUVPercent(getUVSum(map,column,1),getUVSum(map,column,1));
		this.uv2 = getUVPercent(getUVSum(map,column,2),getUVSum(map,column,1));
		this.uv3 = getUVPercent(getUVSum(map,column,3),getUVSum(map,column,1));
		this.uv4 = getUVPercent(getUVSum(map,column,4),getUVSum(map,column,1));
		this.uv5 = getUVPercent(getUVSum(map,column,5),getUVSum(map,column,1));
		this.uv6 = getUVPercent(getUVSum(map,column,6),getUVSum(map,column,1));
		this.uv7 = getUVPercent(getUVSum(map,column,7),getUVSum(map,column,1));
		this.uv8 = getUVPercent(getUVSum(map,column,8),getUVSum(map,column,1));
		this.uv9 = getUVPercent(getUVSum(map,column,9),getUVSum(map,column,1));
		this.uv10 = getUVPercent(getUVSum(map,column,10),getUVSum(map,column,1));
	}
	
	/**
	 * UV汇总
	 * @param map
	 * @param column
	 * @param k
	 * @return
	 */
	public Double getUVSum(Map<String,Object> map,String[] column,int k) {	
		Double sum = 0d;
		for (int i = k-1;i<column.length;i++) {
			Object o = map.get(column[i]);
			Double value = 0d;
			if ( o != null ){
				value = Double.parseDouble(o.toString());
			}
			sum = sum + value;
		}
		return sum;
	}
	/**
	 * 百分比
	 * @param d1
	 * @param d2
	 * @return
	 */
	public Double getUVPercent(Double d1,Double d2) {
		if (d2 == 0) {
			return 0d;
		}		
		BigDecimal percent = new BigDecimal(d1/d2*100);  
		double result = percent.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
		return result;
	}	
	
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getMonDate() {
		return monDate;
	}
	public void setMonDate(String monDate) {
		this.monDate = monDate;
	}
	public String getUvLimit() {
		return uvLimit;
	}
	public void setUvLimit(String uvLimit) {
		this.uvLimit = uvLimit;
	}
	
	
	public Double getUv1() {
		return uv1;
	}

	public void setUv1(Double uv1) {
		this.uv1 = uv1;
	}

	public Double getUv2() {
		return uv2;
	}

	public void setUv2(Double uv2) {
		this.uv2 = uv2;
	}

	public Double getUv3() {
		return uv3;
	}

	public void setUv3(Double uv3) {
		this.uv3 = uv3;
	}

	public Double getUv4() {
		return uv4;
	}

	public void setUv4(Double uv4) {
		this.uv4 = uv4;
	}

	public Double getUv5() {
		return uv5;
	}

	public void setUv5(Double uv5) {
		this.uv5 = uv5;
	}

	public Double getUv6() {
		return uv6;
	}

	public void setUv6(Double uv6) {
		this.uv6 = uv6;
	}

	public Double getUv7() {
		return uv7;
	}

	public void setUv7(Double uv7) {
		this.uv7 = uv7;
	}

	public Double getUv8() {
		return uv8;
	}

	public void setUv8(Double uv8) {
		this.uv8 = uv8;
	}

	public Double getUv9() {
		return uv9;
	}

	public void setUv9(Double uv9) {
		this.uv9 = uv9;
	}

	public Double getUv10() {
		return uv10;
	}

	public void setUv10(Double uv10) {
		this.uv10 = uv10;
	}

	@Override
	public String toString() {
			
		return "periodUV [areaName=" + areaName + ", areaId=" + areaId + ", monDate=" + monDate + ", uvLimit=" + uvLimit + 
				", uv1=" + uv1 + ", uv2=" + uv2 + ", uv3=" + uv3 + ", uv4=" + uv4 + ", uv5=" + uv5 + ", uv6=" + uv6 + 
				", uv7=" + uv7 + ", uv8=" + uv8 + ", uv9=" + uv9 + ", uv10=" + uv10 + "]";
	}
	
}
