package com.funshion.artemis.custom.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;
import com.funshion.artemis.custom.bean.MatBase;

/**
 * 物料基本信息Dao
 * 
 * @author guanzx
 * 
 */
@Repository
public class MatBaseDao extends AbstractJdbcDao {

	/**
	 * 获取物料信息
	 * 
	 * @return
	 */
	public List getAll() {
		String sql = "select * from mat_base order by id desc";
		List list = this.findList(sql, null, MatBase.class);
		return list;
	}

	public MatBase get(Long id) {
		String sql = "select * from mat_base where id = " + id;
		return (MatBase) this.findObject(sql, null, MatBase.class);
	}

	/**
	 * 根据条件获取对应记录
	 * 
	 * @author zhaojj
	 */
	public List getAllMatConditionBeanList(String start, String length, String param) {
		String search = "";
		String[] array = null;
		String searchCondition = "";
		if (param != null && !param.equals("")) {
			param = param.trim();
			if (param.contains(" ")) {
				array = param.split(" ");
				search = array[0];
			} else {
				search = param;
			}
		}

		if (search != null && !search.equals("")) {
			searchCondition = " and (name like '%" + search + "%' or id like '%" + search + "%' or type like '%" + search + "%' or width like '%" + search + "%' or height like '%" + search
					+ "%' or play_dur like '%" + search + "%') ";
		}

		String sql = " SELECT id,name,type,width,height,play_dur as playDur  FROM mat_base " + " WHERE 1=1" + searchCondition;
		if (array != null && array.length > 1) {
			for (int i = 1; i < array.length; i++) {
				search = array[i];
				sql = "select * from ( " + sql + ") s where name like '%" + search + "%' or id like '%" + search + "%' or type like '%" + search + "%' or width like '%" + search
						+ "%' or height like '%" + search + "%' or playDur like '%" + search + "%'";
			}
		}
		sql = sql + " LIMIT " + start + "," + length;

		List list = this.getTemplate().queryForList(sql);
		return list;
	}

	/**
	 * 获取广告物料数目
	 * 
	 * @return
	 */
	public Long getMatCount(String param) {
		String search = "";
		String[] array = null;
		String searchCondition = "";
		if (param != null && !param.equals("")) {
			param = param.trim();
			if (param.contains(" ")) {
				array = param.split(" ");
				search = array[0];
			} else {
				search = param;
			}
		}

		if (search != null && !search.equals("")) {
			searchCondition = " and (name like '%" + search + "%' or id like '%" + search + "%' or type like '%" + search + "%' or width like '%" + search + "%' or height like '%" + search
					+ "%' or play_dur like '%" + search + "%') ";
		}

		String sql = " SELECT id,name,type,width,height,play_dur as playDur  FROM mat_base " + " WHERE 1=1" + searchCondition;
		if (array != null && array.length > 1) {
			for (int i = 1; i < array.length; i++) {
				search = array[i];
				sql = "select * from ( " + sql + ") s where name like '%" + search + "%' or id like '%" + search + "%' or type like '%" + search + "%' or width like '%" + search
						+ "%' or height like '%" + search + "%' or playDur like '%" + search + "%' ";
			}
		}
		sql = "select count(*) from (" + sql + ")s";
		long count = this.getCount(sql, null);
		return count;
	}
	
	/**
	 * 点击物料全选
	 * 
	 */
	public List getMatAllCheckList(String param) {
		String search = "";
		String[] array = null;
		String searchCondition = "";
		if (param != null && !param.equals("")) {
			param = param.trim();
			if (param.contains(" ")) {
				array = param.split(" ");
				search = array[0];
			} else {
				search = param;
			}
		}

		if (search != null && !search.equals("")) {
			searchCondition = " and (name like '%" + search + "%' or id like '%" + search + "%' or type like '%" + search + "%' or width like '%" + search + "%' or height like '%" + search
					+ "%' or play_dur like '%" + search + "%') ";
		}

		String sql = " SELECT id,name,type,width,height,play_dur as playDur  FROM mat_base " + " WHERE 1=1" + searchCondition;
		if (array != null && array.length > 1) {
			for (int i = 1; i < array.length; i++) {
				search = array[i];
				sql = "select * from ( " + sql + ") s where name like '%" + search + "%' or id like '%" + search + "%' or type like '%" + search + "%' or width like '%" + search
						+ "%' or height like '%" + search + "%' or playDur like '%" + search + "%'";
			}
		}
	
		List list = this.getTemplate().queryForList(sql);
		return list;
	}
}
