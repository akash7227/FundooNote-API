package com.akash.Filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import org.apache.log4j.Logger;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import redis.clients.jedis.Jedis;

public class UserFilter implements Filter {
	private static Logger log = Logger.getLogger(UserFilter.class);

	public UserFilter() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		String jwt = httpServletRequest.getHeader("jwtToken");
		log.warn("Token from header" + jwt);

		Jedis jedis = new Jedis("localhost");

		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("FundooNote"))
				.parseClaimsJws(jwt).getBody();

		String id = claims.getId();
		Date Expiration = claims.getExpiration();
		log.warn("Expiration time " + claims.getExpiration());
		log.warn("check id >>" + claims.getId());

		String jwtToken = jedis.hgetAll("Jwt").get("Token");
		log.warn(jwtToken);
		log.warn("Token From Redis >>" + jedis.hgetAll(id).get("Token"));
		String TokenFromRedis = jedis.hgetAll(id).get("Token");

		Date currentTime = new Date();
		log.warn("current time >>" + currentTime);

		if (TokenFromRedis.equals(jwt) && currentTime.compareTo(Expiration) < 0) {
			log.warn(" token verify");
			chain.doFilter(request, response);
		}

	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
