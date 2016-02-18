package ue.ia.neo4j.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

public class AbstractCypherRunner {
	
	/**
	 * 数据源
	 */
	protected final DataSource dataSource;

	public AbstractCypherRunner() {
		this.dataSource = null;
	}

	public AbstractCypherRunner(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * 用实际的参数替换cypher语句中的占位符
	 * 
	 * @author 刘超
	 * @time 2016年1月26日 上午9:58:49
	 * @email liuchao@useease.com
	 * @version 0.1
	 * @param cypher
	 * @param params
	 * @return
	 */
	public String fillCypherParameter(String cypher, Object... params) {
		String newcypher = cypher;
		
		//得到问号的个数
		List<String> cys = new ArrayList<String>();  //保存占位符,用来做判断
		String regex = "(\\?)";
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(cypher);
		while (matcher.find()) {
			cys.add(matcher.group());
		}
		
		int count = cys.size();
		
		if (count > 0) {
			if (params == null || params.length < 1) {
				throw new IllegalArgumentException("参数错误！");
			}
			if(params.length != count) {//参数个数不匹配
				throw new IllegalArgumentException("参数个数不匹配！");
			}
			
			//下面开始赋值
			for (int i=0; i<count; i++) {
				newcypher = newcypher.replaceFirst("\\?", "'" + params[i].toString() + "'");
			}
		} 
		
		System.out.println(newcypher);
		return newcypher;
	}
	
	/**
	 * 关闭result
	 * 
	 * @author 刘超
	 * @time 2016年1月26日 上午10:00:30
	 * @email liuchao@useease.com
	 * @version 0.1
	 * @param rs
	 */
	protected void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {

				e.printStackTrace();
			} finally {
				rs = null;
			}
		}
	}

	/**
	 * 关闭statement
	 * 
	 * @author 刘超
	 * @time 2016年1月26日 上午10:00:43
	 * @email liuchao@useease.com
	 * @version 0.1
	 * @param st
	 */
	protected void close(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {

				e.printStackTrace();
			} finally {
				st = null;
			}
		}
	}

	/**
	 * 关闭连接Connection
	 * 
	 * @author 刘超
	 * @time 2016年1月26日 上午10:00:56
	 * @email liuchao@useease.com
	 * @version 0.1
	 * @param conn
	 */
	protected void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			} finally {
				conn = null;
			}
		}
	}
}
