package com.evstation.charge.login.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.evstation.charge.login.dto.LoginRequestDto;
import com.evstation.charge.login.entity.User;
import com.evstation.charge.login.service.LoginService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login-test")
public class LoginController {
	
	private final LoginService loginSer;
	
	@PostMapping(value = "/userRegister")
	public void userRegister(@RequestBody User user) {
		loginSer.userRegister(user);
	}

	@GetMapping(value = "/allUserSelect")
	public List<LoginRequestDto.Login> allUserSelect() {
		return loginSer.allUserSelect();
	}

	@GetMapping(value = "/userSearch")
	public LoginRequestDto.Login userSearch(@RequestParam Long id) throws Exception {
		return loginSer.userSearch(id);
	}

	@DeleteMapping(value = "/userDel")
	public void userDel(@RequestParam Long id) {
		loginSer.userDel(id);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto.Login userRequestVo){
		
		
		return ResponseEntity.ok(loginSer.login(userRequestVo));
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@Valid @RequestBody LoginRequestDto.Logout userRequestVo){
		
		return ResponseEntity.ok(loginSer.logout(userRequestVo));
	}
	
	@PostMapping(value="/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody LoginRequestDto.SignUp userVo){
		return ResponseEntity.ok(loginSer.signup(userVo));
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<?> getMyUserInfo(){
		return ResponseEntity.ok(loginSer.getMyUserWithAuthorities().get());
	}
	
	@GetMapping("/user/{username}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> getUserInfo(@PathVariable String username){
		return ResponseEntity.ok(loginSer.getUserWithAuthorities(username).get());
	}

}
