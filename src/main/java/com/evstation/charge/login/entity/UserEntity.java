package com.evstation.charge.login.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserEntity {

	
	@Id
	String id;
	
	String pw;
	
	String name;
	
 
	
}
