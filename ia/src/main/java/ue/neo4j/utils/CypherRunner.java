package ue.neo4j.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class CypherRunner extends AbstractCypherRunner {
	public CypherRunner() {
		super();
	}

	public CypherRunner(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * 更新或者创建
	 * 
	 * @author 刘超
	 * @time 2016年1月26日 上午10:01:34
	 * @email liuchao@useease.com
	 * @version 0.1
	 * @param cypher
	 * @param params
	 * @return
	 */
	public int update(String cypher, Object... params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.dataSource.getConnection();
			conn.setAutoCommit(true);   //设置事务的自动提交
			String newcypher = this.fillCypherParameter(cypher, params);
			stmt = conn.prepareStatement(newcypher);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CypherRunnerException(e);
		} finally {
			this.close(stmt);
			this.close(conn);
		}
	}


   /**
    * 更新或者创建，同时手动控制事务
    * 
    * @author 刘超
    * @time 2016年1月26日 上午10:02:00
    * @email liuchao@useease.com
    * @version 0.1
    * @param conn 
    * @param cypher
    * @param params
    * @return
    * @throws SQLException
    */
	public int update(Connection conn, String cypher, Object... params)  throws SQLException {
		PreparedStatement stmt = null;
		try {
			String newcypher = this.fillCypherParameter(cypher, params);
			stmt = conn.prepareStatement(newcypher);
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.close(stmt);
		}
	}


	/**
	 * 查询，将查询结果封装到自定义的ResultSetHandler中
	 * 
	 * @author 刘超
	 * @time 2016年1月26日 上午10:02:42
	 * @email liuchao@useease.com
	 * @version 0.1
	 * @param cypher
	 * @param rsh
	 * @param params
	 * @return
	 */
	public <T> T query(String cypher, ResultSetHandler<T> rsh, Object... params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = this.dataSource.getConnection();
			conn.setAutoCommit(true);
			String newcypher = this.fillCypherParameter(cypher, params);
			stmt = conn.prepareStatement(newcypher);
			rs = stmt.executeQuery();
			
			
			return rsh.handle(rs);  //处理的话先不管
		} catch (Exception e) {
			e.printStackTrace();
			throw new CypherRunnerException(e);

		} finally {
			this.close(rs);
			this.close(stmt);
			this.close(conn);
		}
	}
	
	
	//用来做测试的.......
	public void queryTest(String cypher, Object... params) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = this.dataSource.getConnection();
			conn.setAutoCommit(true);
			String newcypher = this.fillCypherParameter(cypher, params);
			stmt = conn.prepareStatement(newcypher);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CypherRunnerException(e);

		} finally {
			this.close(rs);
			this.close(stmt);
			this.close(conn);
		}
	}

	/**
	 * 手动传入connection时，会进行事务的开启
	 *  
	 * @author 刘超
	 * @time 2016年1月25日 上午11:07:51
	 * @email liuchao@useease.com
	 * @version 0.1
	 * @param conn
	 * @param cypher
	 * @param rsh
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public <T> T query(Connection conn, String cypher, ResultSetHandler<T> rsh,
			Object... params) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String newcypher = this.fillCypherParameter(cypher, params);
			stmt = conn.prepareStatement(newcypher);
			rs = stmt.executeQuery();
			return rsh.handle(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.close(rs);
			this.close(stmt);
		}
	}
}
