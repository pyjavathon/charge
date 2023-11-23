package com.evstation.charge.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evstation.charge.login.entity.LoginEntity;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, String>{

}
