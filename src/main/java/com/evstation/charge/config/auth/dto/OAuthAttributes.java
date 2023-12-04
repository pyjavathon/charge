package com.evstation.charge.config.auth.dto;

import java.util.Map;

import com.evstation.charge.login.entity.Role;
import com.evstation.charge.login.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
	
	private Map<String,Object> attributes;
	
	private String nameAttributeKey;
	
	private String username;
	
	private String email;
	
	@Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String username, String email) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.username = username;
		this.email = email;
	}
	
	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
		return ofGoogle(userNameAttributeName, attributes);
	}

	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
							  .username((String)attributes.get("username"))
							  .email((String)attributes.get("email"))
							  .attributes(attributes)
							  .nameAttributeKey(userNameAttributeName)
							  .build();
	}
	
	public User toEntity() {
		return User.builder()
				   .username(username)
				   .email(email)
				   .role(Role.GUEST)
				   .build();
	}

}
