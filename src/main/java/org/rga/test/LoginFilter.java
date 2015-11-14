package org.rga.test;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rga.test.dao.exceptions.DAOException;
import org.rga.test.dao.impl.UserInMemoryDAOImpl;

@WebFilter(urlPatterns = { "/*" })
public class LoginFilter implements Filter {
	private String whiteList[] = { "/index.html", 
			"/bootstrap", "/css", "/images" };
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpServletResponse httpResponse = (HttpServletResponse) response;

		try {
			if (isLogoutPath(httpRequest)) {
				removeTokenCookie(httpResponse);
				redirectToIndex(httpRequest, httpResponse);
			} else if (isPostLoginPath(httpRequest)) {
				if (checkLogin(httpRequest, httpResponse)) {
					redirectToShowCostumers(httpRequest, httpResponse);
				}
				else{
					redirectToFailLogin(httpRequest, httpResponse);
				}
			} else if (isIndexPath(httpRequest) || checkToken(httpRequest)
					|| isPathInWhiteList(httpRequest)) {
				chain.doFilter(request, response);
			} else {
				redirectToIndex(httpRequest, httpResponse);
			}
		} catch (IllegalArgumentException iae) {
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
		} catch (DAOException e) {
			httpResponse
			.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private boolean isPathInWhiteList(HttpServletRequest httpRequest) {
		boolean found = false;
		for (String path:whiteList) {
			if (httpRequest.getServletPath().startsWith(path)) {
				found = true;
				break;
			}
		}
		return found;
	}

	private void redirectToIndex(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.sendRedirect(request.getContextPath());
	}

	private void redirectToShowCostumers(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.sendRedirect(request.getContextPath() + "/showCustomers.html");
	}
	
	private void redirectToFailLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.sendRedirect(request.getContextPath() + "/index.html?fail=true");
	}

	private void removeTokenCookie(HttpServletResponse response) {
		final Cookie cookie = new Cookie("token", "");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	private boolean checkLogin(HttpServletRequest request,
			HttpServletResponse response) throws DAOException {
		final String login = request.getParameter("login");
		final String password = request.getParameter("password");

		if (login != null && password != null) {
			final String token = new UserInMemoryDAOImpl().checkLogin(login, password);

			if (token == null) {
				return false;
			} else {
				response.addCookie(new Cookie("token", token));

				return true;
			}
		} else {
			return false;
		}
	}

	private boolean checkToken(HttpServletRequest request) throws DAOException,
			IllegalArgumentException {
		final Cookie[] cookies = Optional.ofNullable(request.getCookies())
				.orElse(new Cookie[0]);

		for (Cookie cookie : cookies) {
			if ("token".equals(cookie.getName())) {
				final String token = new UserInMemoryDAOImpl().checkToken(cookie
						.getValue());

				return token != null;
			}
		}

		return false;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	private boolean isIndexPath(HttpServletRequest request) {
		return request.getServletPath().equals("/index.html");
	}
	
	private boolean isLogoutPath(HttpServletRequest request) {
		return request.getServletPath().equals("/logout");
	}
	
	private boolean isPostLoginPath(HttpServletRequest request) {
		return request.getServletPath().equals("/index.html") && request.getMethod()=="POST";
	}
}
