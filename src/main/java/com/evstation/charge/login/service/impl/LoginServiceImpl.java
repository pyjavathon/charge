package com.evstation.charge.login.service.impl;

import org.springframework.stereotype.Service;

import com.evstation.charge.login.entity.LoginEntity;
import com.evstation.charge.login.repository.LoginRepository;
import com.evstation.charge.login.service.LoginService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

	private final LoginRepository loginRepo;
	
	@Override
	public LoginEntity save(LoginEntity joinUser) {
		return loginRepo.save(joinUser);
	}

}
