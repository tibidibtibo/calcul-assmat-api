package fr.deboissieu.calculassmat.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class CustomLogoutHandler implements LogoutHandler {

	/**
	 * For some reason the spring-session logout gets processed before the request
	 * reaches the CORS filter so the response doesn't get the allow-origin header
	 * which then causes the browser to reject the logout response. I tried a bunch
	 * of other methods of trying to include /logout to the CORS filter but they
	 * didn't work so figured a logout handler would be a place I could manually set
	 * the header to persuade the browser to accept the response - it worked!!
	 * 
	 * @param request
	 * @param response
	 * @param authentication
	 */
	@Override
	public void logout(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication) {

		response.setHeader("Access-Control-Allow-Origin", "*");

	}
}