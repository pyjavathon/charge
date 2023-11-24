package com.evstation.charge.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.evstation.charge.login.dto.LoginRequestDto;
import com.evstation.charge.login.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenProvider implements InitializingBean {

	private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

	@Autowired
	private UserRepository userRepository;
	private static final String AUTHORITIES_KEY = "auth";
	
	private final Long accessTokenValidityInMilliseconds;
    private final Long refreshTokenValidityInMilliseconds;
	
	private final String secret;
	
	
	private Key key;
	
	public TokenProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access-token-validity-in-seconds}") Long accessTokenValidityInMilliseconds,
            @Value("${jwt.refresh-token-validity-in-seconds}") Long refreshTokenValidityInMilliseconds) {
        this.secret = secretKey;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInMilliseconds * 1000;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInMilliseconds * 1000;
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public LoginRequestDto.Logout createToken(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		long now = (new Date()).getTime();
		String accesstoken = Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
				.signWith(key, SignatureAlgorithm.HS512)
				.setExpiration(new Date(now + accessTokenValidityInMilliseconds)).compact();

		// Refresh Token 생성
		String refeshtoken = Jwts.builder().signWith(key, SignatureAlgorithm.HS512)
				.setExpiration(new Date(now + refreshTokenValidityInMilliseconds)).compact();

		return LoginRequestDto.Logout.builder().accesstoken(accesstoken)
				.accessTokenExpirationTime(accessTokenValidityInMilliseconds).refreshtoken(refeshtoken)
				.refreshTokenExpirationTime(refreshTokenValidityInMilliseconds).build();
	}

	// 토큰으로부터 정보 추출
	public Claims getClaims(String token) {

		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

	public Authentication getAuthentication(String token) {
		Claims claims = getClaims(token);

		if (claims.get(AUTHORITIES_KEY) == null) {
			throw new RuntimeException("권한 정보가 없는 토큰입니다.");
		}

		Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		User principal = new User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

	// 토큰의 유효성 검증 수행
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			logger.info("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			logger.info("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			logger.info("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			logger.info("JWT 토큰이 잘못되었습니다.");
		}
		return false;
	}

	// blacklist로 등록
	public Long getExpiration(String accessToken) {
		Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody()
				.getExpiration();
		Long now = new Date().getTime();
		return (expiration.getTime() - now);
	}

}
