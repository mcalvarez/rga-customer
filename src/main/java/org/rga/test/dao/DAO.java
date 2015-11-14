package org.rga.test.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class DAO {
	private final static String JNDI_NAME = "java:/comp/env/jdbc/hys1"; 
	
	private final DataSource dataSource;
	
	public DAO() {
		Context initContext;
		try {
			initContext = new InitialContext();
			this.dataSource = (DataSource) initContext.lookup(
				System.getProperty("db.jndi", JNDI_NAME)
			);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}
}
