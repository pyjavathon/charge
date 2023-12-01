package com.evstation.charge.login.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

import com.evstation.charge.jwt.JwtAuthenticationFilter;
import com.evstation.charge.login.dto.LoginRequestDto;
import com.evstation.charge.login.dto.LoginRequestDto.Logout;
import com.evstation.charge.login.entity.User;
import com.evstation.charge.login.service.LoginService;
import com.evstation.charge.validation.ValidationGroups;
import com.evstation.charge.validation.ValidationSequence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
//@RestController
@Controller
@RequestMapping("/login")
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
	
	@GetMapping("/login")
	public String loginForm(Model m) {
		m.addAttribute("user", new LoginRequestDto.Login());
		return "login/login";
	}
	
	@PostMapping("/login")
	public String login(@Validated(ValidationSequence.class) @ModelAttribute("user") LoginRequestDto.Login reqLogin, BindingResult bindRe,HttpServletResponse response){
		
		if(bindRe.hasErrors()) return "login/login";
		
		if(loginSer.loginIDCheck(reqLogin)==0) {
			bindRe.reject("notFound", "일치하는 회원이 없습니다.");
			return "login/login";
		}
		
		if(loginSer.loginPwCheck(reqLogin)==0) {
			bindRe.reject("pwError", "비밀번호가 일치하지 않습니다.");
			return "login/login";
		}
		LoginRequestDto.Logout token = loginSer.loginTokenCheck(reqLogin);
		if(token==null) {
			bindRe.reject("tokenError", "로그인실패(관리자에게 문의)");
		}
		
		Cookie cookie = new Cookie("refreshToken",token.getRefreshtoken());
		cookie.setMaxAge(token.getRefreshTokenExpirationTime().intValue());
		cookie.setHttpOnly(true);
		
		response.setHeader(JwtAuthenticationFilter.AUTHORIZATION_HEADER, token.getAccesstoken());
		response.addCookie(cookie);
		
		return "redirect:/";
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@Valid @RequestBody LoginRequestDto.Logout userRequestVo){
		
		return ResponseEntity.ok(loginSer.logout(userRequestVo));
	}
	
	@GetMapping("signup")
	public String signupForm(Model m) {
		m.addAttribute("user", new LoginRequestDto.SignUp());
		return "login/signup";
	}
	
	@PostMapping(value="/signup")
	public String signup(@Validated(ValidationSequence.class) @ModelAttribute("user") LoginRequestDto.SignUp reqsignup, BindingResult bindRe){
		if(bindRe.hasErrors()) return "login/signup";
		if(loginSer.signupCheck(reqsignup)==1) {
			bindRe.reject("existUser","이미 존재하는 회원입니다.");
			return "login/signup";
		}
			
		loginSer.signup(reqsignup);
		return "redirect:/login/login";
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
