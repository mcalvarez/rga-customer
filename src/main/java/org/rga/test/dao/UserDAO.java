package org.rga.test.dao;

import org.rga.test.dao.exceptions.DAOException;

public interface UserDAO {
	public String checkLogin(String login, String password) throws DAOException;
	public String checkToken(String token) throws DAOException, IllegalArgumentException;
}
