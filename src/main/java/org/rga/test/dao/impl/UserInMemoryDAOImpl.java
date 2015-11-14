package org.rga.test.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.rga.test.dao.UserDAO;
import org.rga.test.dao.exceptions.DAOException;
import org.rga.test.util.LoginUtils;

public class UserInMemoryDAOImpl implements UserDAO {
	private Map<String, String> usersAllowed;

	public UserInMemoryDAOImpl() {
		usersAllowed = new HashMap<String, String>();
		usersAllowed.put("system", "bbc5e661e106c6dcd8dc6dd186454c2fcba3c710fb4d8e71a60c93eaf077f073");
	}

	@Override
	public String checkLogin(String login, String password) throws DAOException {
		final String dbPassword = usersAllowed.get(login);
		final String shaPassword = LoginUtils.encodeSha256(password);

		if (shaPassword.equals(dbPassword)) {
			return LoginUtils.encodeBase64(login + ":" + password);
		} else {
			return null;
		}
	}

	public String checkToken(String token) throws DAOException, IllegalArgumentException {
		final String decodedToken = LoginUtils.decodeBase64(token);
		final int colonIndex = decodedToken.indexOf(':');

		if (colonIndex < 0 || colonIndex == decodedToken.length() - 1) {
			throw new IllegalArgumentException("Invalid token");
		}

		final String login = decodedToken.substring(0, decodedToken.indexOf(':'));
		final String password = LoginUtils.encodeSha256(decodedToken.substring(decodedToken.indexOf(':') + 1));

		if (usersAllowed.containsKey(login)) {
			final String dbPassword = usersAllowed.get(login);

			return password.equals(dbPassword) ? login : null;
		} else {
			return null;
		}
	}
}
