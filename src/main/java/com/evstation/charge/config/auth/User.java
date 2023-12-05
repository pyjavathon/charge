package com.evstation.charge.config.auth;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;
	
	private String email;
	
	@Column(nullable = false)
	private String username;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
	

	@Builder
	public User(String username, String email, Role role) {
		this.username = username;
		this.email = email;
		this.role = role;
	}
	
	public String getRoleKey() {
		return this.role.getKey();
	}
 
	public User update(String username) {
		this.username = username;
		return this;
	}
	
}
