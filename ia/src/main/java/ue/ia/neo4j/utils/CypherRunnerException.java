package ue.ia.neo4j.utils;

/**
 * 自定义异常
 * 
 * @author 刘超
 * @time 2016年1月26日 上午10:04:37
 * @email liuchao@useease.com
 * @version 0.1
 */
@SuppressWarnings("serial")
public class CypherRunnerException extends RuntimeException {

	public CypherRunnerException() {
		super();
	}

	public CypherRunnerException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CypherRunnerException(String message, Throwable cause) {
		super(message, cause);
	}

	public CypherRunnerException(String message) {
		super(message);
	}

	public CypherRunnerException(Throwable cause) {
		super(cause);
	}
}
