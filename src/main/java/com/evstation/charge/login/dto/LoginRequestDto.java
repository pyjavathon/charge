package com.evstation.charge.login.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class LoginRequestDto {
	
	@Getter
	@Setter
	public static class Login{
	@NotNull
	@Size(min = 15, max = 50)
	private String email;
	
	@NotNull
	@Size(min = 3, max = 16)
	private String pw;
	
	}
	
	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Logout{
	
		@NotNull
		private String accesstoken;

		@NotNull
		private String refreshtoken;
		
		private Long accessTokenExpirationTime;
        private Long refreshTokenExpirationTime;
		
	}
	
	@Getter
	@Setter
	public static class SignUp {
		@NotNull
		@Size(min = 2, max = 10)
		private String username;

		@NotNull
		@Size(min = 3, max = 16)
		private String pw;

		@NotNull
		@Size(min = 15, max = 50)
		private String email;

	
	}

}
