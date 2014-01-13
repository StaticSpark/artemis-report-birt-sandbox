package com.funshion.artemis.mc.dao;

import com.funshion.artemis.common.dao.AbstractJdbcDao;
import com.funshion.artemis.mc.bean.McBase;
import com.funshion.artemis.mc.bean.McUv;
import com.funshion.artemis.mc.service.McSqlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 悬浮信息 Dao
 *
 * @author shenwf
 *         Reviewed by
 */
@Repository
public class McReportDao extends AbstractJdbcDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取所有播控
     *
     * @return
     */
    public List getAllMcBase() {
        String sql = "SELECT m.id,m.name,order_name,CONCAT(update_time,' ',u.name) as updateTime,CASE when m.status = 0 then '停用'  when m.status = 1 then '启用'  when m.status = 2 then '暂停' end as status FROM	mc_base m,acct_user u WHERE m.op_user = u.id";
        List list = this.findList(sql, null, McBase.class);
        return list;
    }

    /**
     * 获取播控的日期范围
     *
     * @param monId
     * @return
     */
    public Map<String, Object> getMonDatePeriod(Long monId, String type) {
        String sql = "";
        if("uv".equals(type)) {
        	sql = "SELECT MAX(mon_date) as maxDate, MIN(mon_date) as minDate FROM stg_mc_uv_d s where s.mon_id = " + monId;
        } else if("uv-day".equals(type)){
        	sql = "SELECT MAX(mon_date) as maxDate, MIN(mon_date) as minDate FROM stg_mc_uv_per_d s where s.mon_id = " + monId;
        } else if("concurrent_uv".equals(type)) {
        	sql =  "SELECT MAX(mon_date) as maxDate FROM stg_mc_concurrent_d s where mc_id = " +  monId;
        } else if("concurrent_ip".equals(type)) {
        	sql =  "SELECT MAX(mon_date) as maxDate FROM stg_mc_ip_concurrent_d s where mc_id = " +  monId;
        } else if("stable".equals(type)) {
        	sql =  "SELECT MAX(mon_month) as maxDate FROM stg_mc_stable_uv s";
        } else {
        	sql = "SELECT MAX(mon_date) as maxDate, MIN(mon_date) as minDate FROM ( " +
                    " SELECT DISTINCT(mon_date) as mon_date FROM stg_mc_imp_d where mon_id = " + monId + ") s";
        }
        Map<String, Object> map = this.jdbcTemplate.queryForMap(sql);
        return map;
    }
    

    /**
     * 根据搜索条件查询播控
     *
     * @return
     */
    public List getMcBaseBySearchText(String search, Integer page, Integer rows) {
        String condition = "";
        if (search != null && search.length() > 0) {
            boolean isNum = true;
            //判断是否为数字
            try {
                Long.parseLong(search);
            } catch (Exception e) {
                isNum = false;
            }
            condition = " and (";
            if (isNum) {
                condition += " m.id = " + search + " or ";
            }

            condition += " m.name like '%" + search + "%' or order_name like '%" + search + "%')";
        }

        Integer startNum = (page - 1) * rows;
        Integer endNum = page * rows;

        String sql = "SELECT m.id,m.name,order_name,CONCAT(update_time,' ',u.name) as updateTime,CASE when m.status = 0 then '停用'  when m.status = 1 then '启用'  when m.status = 2 then '暂停' end as status " +
                "FROM	mc_base m,acct_user u WHERE m.op_user = u.id " + condition + " limit " + startNum + "," + endNum;
        List list = this.findList(sql, null, McBase.class);
        return list;
    }

    /**
     * 获取所有播控
     *
     * @return
     */
    public List getMcBaseCountBySearchText(String search) {
        String condition = "";
        if (search != null && search.length() > 0) {
            boolean isNum = true;
            //判断是否为数字
            try {
                Long.parseLong(search);
            } catch (Exception e) {
                isNum = false;
            }
            condition = " and (";
            if (isNum) {
                condition += " m.id = " + search + " or ";
            }

            condition += " m.name like '%" + search + "%' or order_name like '%" + search + "%')";
        }
        String sql = "SELECT m.id,m.name,order_name,CONCAT(update_time,' ',u.name) as updateTime,CASE when m.status = 0 then '停用'  when m.status = 1 then '启用'  when m.status = 2 then '暂停' end as status" +
                " FROM	mc_base m,acct_user u WHERE m.op_user = u.id" + condition;
        List list = this.findList(sql, null, McBase.class);
        return list;
    }

    /**
     * 根据 播控id 获取path
     *
     * @param monId
     * @return
     */
    public List getPathsByMonId(Long monId) {
        String sql = "SELECT DISTINCT(path) as path from stg_mc_imp_d s where s.mon_id =" + monId +
                " union SELECT DISTINCT(path) as path from stg_mc_clk_d s where s.mon_id =" + monId +
                " union SELECT DISTINCT(path) as path from stg_mc_imp_choke_d where mon_id = " + monId;
        List list = this.getTemplate().queryForList(sql, Long.class);
        list.remove(null);
        return list;
    }

    /**
     * 根据播控、地域、日期获取相关的path
     *
     * @param monId
     * @param areaId
     * @param mondDate
     * @return
     */
    public List getPathsByMonAreaDate(Long monId, String areaId, String monDate) {
        String sql = "SELECT DISTINCT(path) as path from stg_mc_imp_d s where s.mon_id =" + monId + " and s.area_id = " + areaId + " AND s.mon_date = '" + monDate +
                "' union SELECT DISTINCT(path) as path from stg_mc_clk_d s where s.mon_id =" + monId + " and s.area_id = " + areaId + " AND s.mon_date = '" + monDate + "'" +
                " union SELECT DISTINCT(path) as path from stg_mc_imp_choke_d s where s.mon_id =" + monId + " and s.area_id = " + areaId + " AND s.mon_date = '" + monDate + "'";
        List list = this.getTemplate().queryForList(sql, Long.class);
        list.remove(null);
        return list;
    }

    /**
     * 根据播控id和path、地域获取path和对应的点击率控制
     *
     * @param mcId
     * @param path
     * @param areaId
     * @return
     */
    public List getMcClkCtrl(Long mcId, Long areaId) {
        String sql = " select * from ( SELECT s.path, s.clkrate_ctrl from stg_mc_clk_d s WHERE s.mon_id = " + mcId + " and s.area_id = " + areaId + " GROUP BY s.path union " +
                "SELECT s.path, '' as clkrate_ctrl from stg_mc_imp_d s WHERE s.mon_id = " + mcId + " and s.area_id = " + areaId + " GROUP BY s.path  union " +
                "SELECT s.path, '' as clkrate_ctrl from stg_mc_imp_choke_d s WHERE s.mon_id = " + mcId + " and s.area_id = " + areaId + " GROUP BY s.path) s group by s.path";
        List list = this.jdbcTemplate.queryForList(sql);
        return list;
    }

    /**
     * 根据id获取播控项
     *
     * @param id
     * @return
     */
    public McBase getMcBaseById(Long id) {
        String sql = "select id, name, update_time, status from mc_base where id = " + id;
        return (McBase) this.findObject(sql, null, McBase.class);
    }

    //根据uvId获取对象
    public McUv getMcUvById(Long uvId) {
        String sql = "select * from mc_uv where id = " + uvId;
        McUv mcUv = null;
        try {
            mcUv = (McUv) this.findObject(sql, null, McUv.class);
        } catch (Exception e) {
            return null;
        }
        return mcUv;
    }

    /**
     * 根据播控id/时间以及并发报告类型获得sql
     *
     * @param monId
     * @param monDate
     * @param type
     * @return
     */
    public List<Map<String, Object>> getConcurrentDateBymonIdandMonDate(String monId, String monDate, String type) {
        String conditions = "";
        String sql = null;
        if (monId != null && !monId.equals("null")) {
            conditions = " mc_id =" + monId;
        } else {
            conditions = " mc_id ='" + (-1) + "'";
        }
        if (monDate != null && !monDate.equals("null")) {
            conditions += " and mon_date = '" + monDate + "'";
        }

        if (type.equals("uv")) {
            sql = " select * from stg_mc_concurrent_d where " + conditions + " order by sort_order,period";
        } else {
            sql = " select * from stg_mc_ip_concurrent_d where " + conditions + " order by sort_order,period";
        }
        List<Map<String, Object>> list = this.template.queryForList(sql);
        return list;
    }

    /**
     * 根据查询条件获得播控UV超频地域分布数据
     *
     * @param monType
     * @param monDate
     * @return
     */
    public List<Map<String, Object>> getMcOverLockAreaData(String monId, String startDate, String endDate, String areaId) {

        String conditions = "";

        if (isRational(monId)) {
            conditions = " mon_id = " + monId;
        }

        if (isRational(areaId)) {
            conditions += " and s.area_id in (" + areaId + ")";
        }

		/*if (isRational(startDate)) {
            conditions += " and mon_date >= '" + startDate +"'";
		}*/

        if (isRational(endDate)) {
            conditions += " and mon_date = '" + endDate + "'";
        }
        if (conditions.isEmpty()) {
            conditions = " 1=1 ";
        }

        String sql = McSqlManager.getSqlByCondition(conditions);
        List<Map<String, Object>> list = this.template.queryForList(sql);
        return list;
    }

    /**
     * 根据查询条件获得播控UV超频日期分布数据
     *
     * @param monType
     * @param monDate
     * @return
     */
    public List<Map<String, Object>> getMcOverLockDateData(String monId, String startDate, String endDate, String areaId) {

        String conditions = "";

        if (isRational(monId)) {
            conditions = " mon_id = " + monId;
        }

        if (isRational(areaId)) {
            conditions += " and s.area_id in (" + areaId + ")";
        }

        if (isRational(startDate)) {
            conditions += " and mon_date >= '" + startDate + "'";
        }

        if (isRational(endDate)) {
            conditions += " and mon_date <= '" + endDate + "'";
        }
        if (conditions.isEmpty()) {
            conditions = " 1=1 ";
        }

        String sql = McSqlManager.getSqlByCondition(conditions);
        List<Map<String, Object>> list = this.template.queryForList(sql);
        return list;
    }

    /**
     * 判断参数是否合理
     *
     * @param field
     * @return
     */
    public boolean isRational(String field) {
        boolean flag = false;
        if (field != null && !field.equals("null") && !field.isEmpty()) {
            flag = true;
        }
        return flag;
    }
    
    public List getAllStableUv(String monType, String monDate) {
        monDate = monDate.replace("-", "");

        String sql = "select * from stg_mc_stable_uv where mon_type = '" + monType
                + "' and mon_month = '" + monDate + "' order by gap_date";

        return this.template.queryForList(sql);
    }

    /**
     * 通过播控Id，地域Id，和日期范围获取播控UV数据
     *
     * @return
     */
    public List<Map<String, Object>> getUVDataByMcIdAreaAndDateRange(String monId, String fromDate, String toDate, String areaId) {
        String sql = getUVDataQueryString("stg_mc_uv_d", monId, fromDate, toDate, areaId);
        List<Map<String, Object>> result = this.jdbcTemplate.queryForList(sql);
        return result;
    }

    String getUVDataQueryString(String tableName, String monId, String fromDate, String toDate, String areaId) {
        String sqlTemplate = "select %s from %s where %s";
        String columns = " mon_date,area_id,uv_stat,uvn ";
        StringBuilder conditionTmplateBuilder = new StringBuilder(" 1=1 ");
        if (monId != null && !monId.isEmpty()) {
            conditionTmplateBuilder.append(" and mon_id=" + monId);
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            conditionTmplateBuilder.append(" and mon_date >= '" + fromDate + "' ");
        }
        if (toDate != null && !toDate.isEmpty()) {
            conditionTmplateBuilder.append(" and mon_date <= '" + toDate + "' ");
        }
        if (areaId != null && !areaId.isEmpty()) {
            conditionTmplateBuilder.append(" and area_id in (" + areaId + ")");
        }

        String sql = String.format(sqlTemplate, columns, tableName, conditionTmplateBuilder);
        return sql;
    }

    public List<Map<String, Object>> getDailyUVDataByMcIdAreaAndDateRange(String monId, String monDateFrom, String monDateTo, String areaIds) {
        String queryString = this.getUVDataQueryString("stg_mc_uv_per_d", monId, monDateFrom, monDateTo, areaIds);
        List<Map<String, Object>> result = this.jdbcTemplate.queryForList(queryString);
        return result;
    }
}
