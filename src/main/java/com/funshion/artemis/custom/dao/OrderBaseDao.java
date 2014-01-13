package com.funshion.artemis.custom.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.funshion.artemis.common.dao.AbstractJdbcDao;
import com.funshion.artemis.custom.bean.OrderBase;
import com.funshion.artemis.custom.bean.OrderConditionBean;
import com.funshion.artemis.user.bean.User;

/**
 * 订单基本Dao
 * @author guanzx
 *
 */
@Repository
public class OrderBaseDao extends AbstractJdbcDao {
	
	/**
	 * 获取订单信息列表
	 * @return
	 */
	public List getAll() {
		String sql = "select * from ord_base  order by id desc";
		List list = this.findList(sql, null, OrderBase.class);
		return list;
	}
	
	public OrderBase get(Long id) {
		String sql = "select * from ord_base where id = " + id;
		return (OrderBase) super.findObject(sql, null, OrderBase.class);
	}

	/**
	 * 根据客户id获取订单信息
	 * @param id
	 * @return
	 */
	public List getByAccountId(Long id) {
		String sql = "select * from ord_base where account_id = " + id;
		List list = this.findList(sql, null, OrderBase.class);
		return list;
	}

	/**
	 * 根据销售Id获取订单信息
	 * @param id
	 * @return
	 */
	public List getBySalesId(Long id) {
		String sql = "select * from ord_base where sales_id = " + id;
		List list = this.findList(sql, null, OrderBase.class);
		return list;
	}

	/**
	 * 小时条件列表
	 * 
	 * @return
	 */
	public List getSaleConditionList() {
		String sql = "SELECT u.id,u.name FROM ord_base o, acct_user u WHERE o.sales_id = u.id GROUP BY u.id  order by u.id desc ";
		List list = this.findList(sql, null, User.class);
		return list;
	}

	/**
	 * 订单条件列表
	 * 
	 * @return
	 */
	public List getOrderConditionList() {
		String sql = "SELECT o.id as id, o.name as name, ae.name as medUserName,sale.name as saleName, o.amount as price   from ord_base o "
				+ "LEFT JOIN (SELECT * from acc_base a where a.acc_type = 0) ac ON o.account_id = ac.id LEFT JOIN "
				+ " (SELECT * from acc_base a where a.acc_type = 1) agent ON o.agency_id = agent.id LEFT JOIN acct_user ae ON o.ae_id = ae.id " + " LEFT JOIN acct_user sale ON o.sales_id = sale.id order by o.id desc  ";
		List list = this.findList(sql, null, OrderConditionBean.class);
		return list;
	}
}
