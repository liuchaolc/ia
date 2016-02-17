package ue.neo4j.utils;

import java.sql.ResultSet;

public interface ResultSetHandler<T> {

	 T handle(ResultSet rs);
}
