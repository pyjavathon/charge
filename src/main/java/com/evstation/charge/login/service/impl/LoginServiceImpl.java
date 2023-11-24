package com.evstation.charge.login.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evstation.charge.jwt.JwtAuthenticationFilter;
import com.evstation.charge.jwt.TokenProvider;
import com.evstation.charge.login.dto.LoginRequestDto;
import com.evstation.charge.login.dto.LoginRequestDto.Login;
import com.evstation.charge.login.entity.Authority;
import com.evstation.charge.login.entity.User;
import com.evstation.charge.login.repository.UserRepository;
import com.evstation.charge.login.service.LoginService;
import com.evstation.charge.util.SecurityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService, UserDetailsService {

	private final PasswordEncoder passwordEncoder;
	@Autowired
	private TokenProvider tokenProvider;
	@Autowired
	private RedisTemplate redisTemplate;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	
	@Autowired
	UserRepository userRepo;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public void userRegister(User user) {
		userRepo.save(user);
	}

	@Override
	public List<LoginRequestDto.Login> allUserSelect() {
		List<User> selectList = userRepo.findAll();

		// List<UserVo> resultList =
		// Arrays.asList(modelMapper.map(search,UserVo[].class));

		List<LoginRequestDto.Login> resultList = selectList.stream()
				.map(m -> modelMapper.map(m, LoginRequestDto.Login.class)).collect(Collectors.toList());

		return resultList;
	}

	@Override
	public LoginRequestDto.Login userSearch(Long id) throws Exception {
		Optional<User> user = userRepo.findById(id);

		if (!user.isPresent())
			throw new UsernameNotFoundException("일치하는 회원을 찾을 수 없습니다.");

		LoginRequestDto.Login searchResult = modelMapper.map(user, LoginRequestDto.Login.class);
		return searchResult;
	}

	@Override
	public void userDel(Long id) {
		userRepo.deleteById(id);

	}

	

	@Override
	public User signup(LoginRequestDto.SignUp userVo) {
		if (userRepo.findOneWithAuthoritiesByEmail(userVo.getEmail()).orElse(null) != null) {
			throw new RuntimeException("이미 가입되어 있는 유저입니다.");
		}

		Authority authority = Authority.builder().authorityName("ROLE_USER").build();

		User user = User.builder()
									.username(userVo.getUsername())
									.pw(passwordEncoder.encode(userVo.getPw()))
									.email(userVo.getEmail())
									.authorities(Collections.singleton(authority))
									.build();

		return userRepo.save(user);

	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthorities(String userEmail) {
		return userRepo.findOneWithAuthoritiesByEmail(userEmail);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> getMyUserWithAuthorities() {
		return SecurityUtil.getCurrentUsername().flatMap(userRepo::findOneWithAuthoritiesByEmail);
	}

	@Override
	public ResponseEntity<?> logout(LoginRequestDto.Logout userRequestVo) {
		// 1. Access Token 검증
		if (!tokenProvider.validateToken(userRequestVo.getAccesstoken()))
			return ResponseEntity.badRequest().body("유효하지 않은 토큰입니다.");

		// 2. Access Token 에서 User email 을 가져옵니다.
		Authentication authentication = tokenProvider.getAuthentication(userRequestVo.getAccesstoken());

		
		// 3. Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
		if (redisTemplate.opsForValue().get("RT: " + authentication.getName()) != null) {
			// Refresh Token 삭제
			redisTemplate.delete("RT: " + authentication.getName());
		}
		// 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
		Long expiration = tokenProvider.getExpiration(userRequestVo.getAccesstoken());

		redisTemplate.opsForValue().set(userRequestVo.getAccesstoken(), "logout", expiration, TimeUnit.MILLISECONDS);

		return ResponseEntity.ok("로그아웃 되었습니다.");
	}

	@Override
	public ResponseEntity<?> login(Login userRequestVo) {
		if(userRepo.findByEmail(userRequestVo.getEmail()).orElse(null)==null) return ResponseEntity.badRequest().body("해당하는 회원이 없습니다.");
			
	    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userRequestVo.getEmail(), userRequestVo.getPw());
	    Authentication authentication = null;
	    LoginRequestDto.Logout jwt = null;
	    try {
		 authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		
	    }catch(Exception e) {
	    	return ResponseEntity.badRequest().body("로그인 실패 (비밀번호가 일치하지 않습니다.)");
	    }
	    
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		try {
		jwt = tokenProvider.createToken(authentication);
		
		ObjectMapper mapper = new ObjectMapper();
		
        String tokenString = mapper.writeValueAsString(jwt);
        
        redisTemplate.opsForValue().set("RT: " + authentication.getName(), tokenString, jwt.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
		
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e+"로그인 실패(관리자에게 문의)");
		}
		
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JwtAuthenticationFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
		return ResponseEntity.ok(jwt);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String userEmail) {
		return userRepo.findOneWithAuthoritiesByEmail(userEmail).map(user -> createUser(userEmail, user))
				.orElseThrow(() -> new UsernameNotFoundException(userEmail + " -> DB에서 찾을 수 없습니다."));
	}

	private org.springframework.security.core.userdetails.User createUser(String email, User user) {

		List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
				.collect(Collectors.toList());

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPw(),
				grantedAuthorities);
	}



}
