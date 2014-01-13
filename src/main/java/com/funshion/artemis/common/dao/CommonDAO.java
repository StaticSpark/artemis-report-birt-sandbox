package com.funshion.artemis.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;



/**
 * 通用的DAO组件，如分页功能
 * @author laiss
 *
 */
public class CommonDAO extends AbstractJdbcDao {

    
    /**
     * 获取该SQL返回的记录条数.
     * @param hsql hql语句
     * @param params 查询参数
     * @return 总条数
     * @throws AppException
     */
    private Long getResultCnt(String sql, List<Object> params) {

		// 删除查询语句中的order by子句
		String newSql = delOrderbySQL(sql);

		// 修改SQL语句
		int idx = newSql.toUpperCase().indexOf("FROM ");
		newSql = "select count(*) " + newSql.substring(idx);
		return new Long(getCount(newSql, params));
	}

    /**
     * 删除查询条件中的order by子句
     * @param queryString 查询SQL语句
     * @return 删除查询语句中的order by子句后的查询语句
     */
    private String delOrderbySQL(String queryString) {

		String result = queryString;

		int idx = queryString.toLowerCase().indexOf("order by");

		if (idx > 0) {
			result = queryString.substring(0, idx);
		}
		return result;
	}

    /**
     * 分页用的ResultSet处理类，以<code>com.doudou.common.dbbase.Row</code>对象的方式返回结果.
     * @author laiss
     *
     */
    private class PaginationResultSetExtractor implements ResultSetExtractor {
		int startRow;
		int pageSize;

		public PaginationResultSetExtractor(int startRow, int pageSize) {
			this.startRow = startRow;
			this.pageSize = pageSize;
		}

		public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
			final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			int currentRow = 0;
			while (rs.next() && currentRow < startRow + pageSize) {
				if (currentRow >= startRow) {
					result.add((Map) new ColumnMapRowMapper().mapRow(rs, currentRow));
				}
				currentRow++;
			}
			List<Row> rows = buildRows(result);
			return rows;
		}
	}
    
    /**
     * 分页用的ResultSet处理类，以指定对象类型的方式返回结果.
     * @author laiss
     *
     */
    private class PaginationResultSetBeanExtractor extends PaginationResultSetExtractor {
    	Class targetClass = null;
		public PaginationResultSetBeanExtractor(int startRow, int pageSize, Class clz) {
			super(startRow, pageSize);
			this.targetClass = clz;
		}

		public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
			final List<Object> result = new ArrayList<Object>();
			int currentRow = 0;
			while (rs.next() && currentRow < startRow + pageSize) {
				if (currentRow >= startRow) {
					result.add(new BeanPropertyRowMapper(targetClass).mapRow(rs, currentRow));
				}
				currentRow++;
			}
			return result;
		}
	}
}

