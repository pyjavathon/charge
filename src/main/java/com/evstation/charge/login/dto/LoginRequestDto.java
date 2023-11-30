package com.evstation.charge.login.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.evstation.charge.validation.ValidationGroups;
import com.evstation.charge.validation.ValidationGroups.SizeCheckGroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class LoginRequestDto {
	
	@Getter
	@Setter
	public static class Login{
	@NotEmpty
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
	@Size(min = 15, max = 50)
	private String email;
	
	@NotEmpty
	@Size(min = 3, max = 16)
	private String pw;
	
	}
	
	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Logout{
	
		@NotEmpty
		private String accesstoken;

		@NotEmpty
		private String refreshtoken;
		
		private Long accessTokenExpirationTime;
        private Long refreshTokenExpirationTime;
		
	}
	
	@Getter
	@Setter
	public static class SignUp {
		
		@NotEmpty(groups = ValidationGroups.NotEmptyCheckGroup.class)
		private String username;

		@NotEmpty(groups = ValidationGroups.NotEmptyCheckGroup.class)
		@Size(min = 3, max = 16, groups = ValidationGroups.SizeCheckGroup.class)
		private String pw;

		@NotEmpty(groups = ValidationGroups.NotEmptyCheckGroup.class)
		@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.", groups = ValidationGroups.PatternCheckGroup.class)
		private String email;

	
	}

}
