package com.akash.service;

import java.security.Key;
import java.sql.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service("tokenService")
public class TokenService {
	String name = "FundooNote";
	private static Logger log = Logger.getLogger(TokenService.class);

	//  method to construct a JWT
	public String createJWT(String id, String issuer, String subject, long ttlMillis) 
	{
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(ttlMillis);
		name.getBytes();

		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(name);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		JwtBuilder builder = Jwts.builder()
								 .setId(id)
								 .setIssuedAt(now)
								 .setSubject(subject)
								 .setIssuer(issuer)
								 .signWith(signatureAlgorithm, signingKey);

		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		return builder.compact();

	}

	public void parseJwt(String jwt) {
		Claims claims = Jwts.parser()
						.setSigningKey(DatatypeConverter.parseBase64Binary(name))
						.parseClaimsJws(jwt)
						.getBody();
		log.warn("id :" + claims.getId());
		log.warn("Subject :" + claims.getSubject());
		log.warn("issuer :" + claims.getIssuer());
		log.warn("Expiration :" + claims.getExpiration());

	}

}
