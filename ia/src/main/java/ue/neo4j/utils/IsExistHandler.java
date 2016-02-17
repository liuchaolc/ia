package ue.neo4j.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IsExistHandler implements ResultSetHandler<Boolean>{

	@Override
	public Boolean handle(ResultSet rs) {
		
		try {
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
