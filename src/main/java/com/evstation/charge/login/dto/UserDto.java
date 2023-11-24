package com.evstation.charge.login.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.evstation.charge.login.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private String email;
	
	private String pw;
	
	private String username;
	
	private Set<AuthorityDto> authorityDtoSet;
	
	public static UserDto from(User user) {
		if(user == null) return null;
		
		return UserDto.builder()
				.email(user.getEmail())
				.username(user.getUsername())
				.authorityDtoSet(user.getAuthorities().stream().map(authority -> AuthorityDto
																					.builder()
																					.authorityName(authority.getAuthorityName())
																					.build())
															   .collect(Collectors.toSet()))
															   .build();
	}

}
