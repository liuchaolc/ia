package ue.neo4j.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils {

	private static DataSource ds = new ComboPooledDataSource();
	private static ThreadLocal<Connection> t = new ThreadLocal<Connection>();

	public static DataSource getDataSource() {
		return ds;
	}

	/**
	 * 获得一个开启事务的连接
	 * 
	 * @author 刘超
	 * @time 2016年1月25日 上午9:22:29
	 * @email liuchao@useease.com
	 * @version 0.1
	 * @return
	 */
	public static Connection getConnection() {
		try {
			Connection conn = t.get();
			if (null == conn) {
				conn = ds.getConnection();
				conn.setAutoCommit(false);
				t.set(conn);
			}
			return conn;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 提交事务
	 * 
	 * @author 刘超
	 * @time 2016年1月25日 上午9:23:35
	 * @email liuchao@useease.com
	 * @version 0.1
	 */
	public static void commitTransaction() {
		try {
			Connection conn = t.get();
			if (conn != null) {
				conn.commit();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 进行事物的回滚
	 * 
	 * @author 刘超
	 * @time 2016年1月25日 上午9:23:48
	 * @email liuchao@useease.com
	 * @version 0.1
	 */
	public static void rollbackTransaction() {
		try {
			Connection conn = t.get();
			if (conn != null) {
				conn.rollback();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 关闭连接
	 * 
	 * @author 刘超
	 * @time 2016年1月25日 上午9:24:02
	 * @email liuchao@useease.com
	 * @version 0.1
	 */
	public static void closeConn() {
		try {
			Connection conn = t.get();
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			t.remove();
		}
	}
}
