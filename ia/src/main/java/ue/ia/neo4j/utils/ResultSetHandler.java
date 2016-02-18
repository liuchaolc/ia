package ue.ia.neo4j.utils;

import java.sql.ResultSet;

public interface ResultSetHandler<T> {

	 T handle(ResultSet rs);
}
