package com.evstation.charge.login.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.evstation.charge.login.dto.LoginRequestDto;
import com.evstation.charge.login.dto.UserDto;
import com.evstation.charge.login.dto.LoginRequestDto.Login;
import com.evstation.charge.login.entity.User;

public interface LoginService {

	public void userRegister(User user);

	public List<LoginRequestDto.Login> allUserSelect();

	public LoginRequestDto.Login userSearch(Long id) throws Exception;

	public void userDel(Long id);

	void signup(LoginRequestDto.SignUp userVo);

	Optional<User> getUserWithAuthorities(String userEmail);

	Optional<User> getMyUserWithAuthorities();

	public Object logout(LoginRequestDto.Logout userRequestVo);

	public ResponseEntity<?> login(LoginRequestDto.Login userRequestVo);
	
	int loginIDCheck(LoginRequestDto.Login reqLogin);
	
	int loginPwCheck(Login userRequestVo);
	
	LoginRequestDto.Logout loginTokenCheck(Login userRequestVo);
	
	int signupCheck(LoginRequestDto.SignUp userVo);

}
