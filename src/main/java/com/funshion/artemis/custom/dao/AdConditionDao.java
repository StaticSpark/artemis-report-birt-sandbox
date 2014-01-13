package com.funshion.artemis.custom.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;

/**
 * 广告查询条件Dao
 * @author guanzx
 *
 */
@Repository
public class AdConditionDao extends AbstractJdbcDao{
	
	/**
	 * 获取广告查询条件
	 * @return
	 */
	public List getAllAdConditionBeanList(String start, String length, String param) {
		String searchCondition =  "";
		String limitStr = "";
		String sql = "";
		String search = "";
		String[] array = null;
		if(param != null && !param.equals("")) {
			param = param.trim();
			if(param.contains(" ")) {
				array = param.split(" ");
				search = array[0];
			} else {
				search = param;
				limitStr = " limit " + start + "," + length;
			}
			searchCondition = " and (a.name like '%" + search + "%' or a.id like '%"+search+"%' or o.name like '%"+search+"%') " ;
		} else {
			limitStr = " limit " + start + "," + length;
		}	
		sql = " SELECT adId,adName,isDelete,orderName,d.name as statusName,s.maxDate,s.minDate " +
				"from " +
				"(  SELECT a.id as adId,a.name as adName,a.isdelete as isDelete,o.name as orderName,get_ad_status(a.id) as ad_status,max(sch.pub_date) as  maxDate,min(sch.pub_date) as minDate " +
					"FROM  ad_base a LEFT JOIN  sch_plan_p sch on a.id = sch.ad_id  LEFT JOIN ord_base o ON a.ord_id = o.id  " +
					"WHERE a.ad_process = 4  " + searchCondition +
					"GROUP BY a.id order by a.id desc " +
				") s , dim_ad_status d " +
				"WHERE  d.id = s.ad_status " +
				"GROUP BY s.adId " +
				"ORDER BY d.id,s.adId DESC"+ limitStr;
	    if(array != null && array.length > 1) {
	    	for(int i = 1; i < array.length; i++) {
	    		sql = "select * from ("+sql+") s where s.adName like '%" + array[i] + "%' or adId like '%"+array[i]+"%' or orderName like '%"+array[i]+
	    				"%' or statusName like '%"+array[i]+"%' " ;
	    	}
	    	sql = sql + " limit " + start + "," + length;
	    }
	    
		List list = this.findList(sql, null);		
		return list;			
	}
	
	/**
	 * 点击全选时的查询
	 * @param param
	 * @return
	 */
	public List getAdAllCheckList(String param) {
		String searchCondition =  "";
		String sql = "";
		String search = "";
		String[] array = null;
		if(param != null && !param.equals("")) {
			param = param.trim();
			if(param.contains(" ")) {
				array = param.split(" ");
				search = array[0];
			} else {
				search = param;
			}
			searchCondition = " and (a.name like '%" + search + "%' or a.id like '%"+search+"%' or o.name like '%"+search+"%') " ;
		} 
		sql = " SELECT adId,adName,isDelete,orderName,d.name as statusName,s.maxDate,s.minDate " +
				"from " +
				"(  SELECT a.id as adId,a.name as adName,a.isdelete as isDelete,o.name as orderName,get_ad_status(a.id) as ad_status,max(sch.pub_date) as  maxDate,min(sch.pub_date) as minDate " +
					"FROM  ad_base a LEFT JOIN  sch_plan_p sch on a.id = sch.ad_id  LEFT JOIN ord_base o ON a.ord_id = o.id  " +
					"WHERE a.ad_process = 4  " + searchCondition +
					"GROUP BY a.id order by a.id desc " +
				") s , dim_ad_status d " +
				"WHERE  d.id = s.ad_status " +
				"GROUP BY s.adId " +
				"ORDER BY d.id,s.adId DESC";
	    if(array != null && array.length > 1) {
	    	for(int i = 1; i < array.length; i++) {
	    		sql = "select * from ("+sql+") s where s.adName like '%" + array[i] + "%' or adId like '%"+array[i]+"%' or orderName like '%"+array[i]+
	    				"%' or statusName like '%"+array[i]+"%' " ;
	    	}
	    }    
		List list = this.findList(sql, null);		
		return list;			
	}
	
	
	/*
	 * 根据广告id以及广告名称精确查询的列表
	 */
	public List getPreciseAdConditionBeanList(String param) {
		String searchCondition =  "";
		String sql = "";
		List list = new ArrayList();
		String columns[] = null;
		//参数中若有逗号，则说明是组合查询
		if(param != null && !param.equals("")) {			
			if (param.contains(",")) {
				columns = param.split(",");
				StringBuilder stringBuilder = new StringBuilder();
				for (int i=0;i<columns.length;i++) {
					stringBuilder.append("'"+columns[i]+"'").append(",");
				}			
				param = stringBuilder.toString().substring(0, stringBuilder.toString().length()-1);	
				searchCondition = " and (a.name in ("+ param +") or a.id in (" +param+ "))" ;
			}else {
				searchCondition = " and (a.name in ('"+ param +"') or a.id in ('" +param+ "'))" ;		
			}				
		}
						
		sql = "select adId,adName,IsDelete,orderName,d.name as statusName,s.maxDate,s.minDate" +
              " from ( " +
               " select a.id as adId,a.name as adName,a.isdelete as isDelete,o.name as orderName,get_ad_status(a.id) as ad_status,max(sch.pub_date) as maxDate,min(sch.pub_date) as minDate"+
               " FROM  ad_base a LEFT JOIN  ord_base o ON a.ord_id = o.id LEFT JOIN sch_plan_p sch on a.id = sch.ad_id"+
               " where a.ad_process = 4 "+ searchCondition +
               " group by a.id ) s "+
              " left join dim_ad_status d "+
              " on d.id = s.ad_status"+
              " group by s.adId " +
			  " order by d.id,s.adId desc";   

	    list = this.findList(sql, null);
		return list;			
	}
	
	/**
	 * 获取广告数目
	 * @return
	 */
	public Long getAdCount(String param) {
		String searchCondition =  "";
		String sql = "";
		String search = "";
		String statusCondtion = "''";
		String[] array = null;
		if(param != null && !param.equals("")) {
			param = param.trim();
			if(param.contains(" ")) {
				array = param.split(" ");
				search = array[0];
			} else {
				search = param;
			}
			searchCondition = " and (a.name like '%" + search + "%' or a.id like '%"+search+"%' or o.name like '%"+search+"%' or d.name like '%" + search + "%' or p.name like '%"+search+"%') " ;
			sql = " SELECT adName,isDelete,adId,catName,orderName,priority,weight,adpName, d.name as statusName,s.log_time from ( " +
					"SELECT " +
					"a.name as adName, a.isdelete as isDelete, a.id as adId, d.name as catName,o.name as orderName,a.priority, a.weight,  p.name as adpName , get_ad_status(a.id) as ad_status, a.log_time" +
					" FROM " +
					" ad_base a LEFT JOIN  ord_base o ON a.ord_id = o.id " +
			        "  LEFT JOIN ad_adp aa ON a.id = aa.ad_id " +
			        " LEFT JOIN adp_base p ON aa.adp_id = p.id " +
					"	LEFT JOIN " +
					"	dim_ad_category d ON a.cat_id = d.id " +
					" WHERE" +
					" a.ad_process = 4  " + searchCondition +
					" GROUP BY" +
						" a.id " +
					" order by" +
						" a.id desc " +
				    " ) s , dim_ad_status d WHERE  d.id = s.ad_status GROUP BY s.adId ORDER BY s.log_time DESC ";
			if(array != null) {
		    	for(int i = 1; i < array.length; i++) {
		    		sql = "select * from ("+sql+") s where s.adName like '%" + array[i] + "%' or adId like '%"+array[i]+"%' or catName like '%"+array[i]+"%' or orderName like '%"+array[i]+
		    				"%' or adpName like '%"+array[i]+"%' or statusName like '%"+array[i]+"%' " ;
		    	}
		    }
		} else {
			sql = "SELECT " +
					"a.name as adName, a.isdelete as isDelete, a.id as adId, d.name as catName,o.name as orderName,a.priority, a.weight,  p.name as adpName , '' as ad_status, a.log_time" +
					" FROM " +
					" ad_base a LEFT JOIN  ord_base o ON a.ord_id = o.id " +
			        "  LEFT JOIN ad_adp aa ON a.id = aa.ad_id " +
			        " LEFT JOIN adp_base p ON aa.adp_id = p.id " +
					"	LEFT JOIN " +
					"	dim_ad_category d ON a.cat_id = d.id " +
					" WHERE" +
					" a.ad_process = 4  " + searchCondition + " GROUP BY a.id ";
		}
		
	    sql = "select count(*) from (" + sql + ") s";
		long count = this.getCount(sql, null);
		return count;
	}

	@Override
	protected List findList(String sql, List<Object> params) {
		List<Map<String, Object>> list = null;
		if (params == null) {
			list = template.queryForList(sql);
		} else {
			list = template.queryForList(sql, params.toArray());
		}
		return list;
	}


}
