package com.evstation.charge.config.auth.dto;

import java.io.Serializable;

import com.evstation.charge.config.auth.User;

import lombok.Getter;

@Getter
public class SessionUser implements Serializable {
	
	private String username;
	private String email;
	
	public SessionUser(User user) {
		this.username = user.getUsername();
		this.email = user.getEmail();
		
	}

}
