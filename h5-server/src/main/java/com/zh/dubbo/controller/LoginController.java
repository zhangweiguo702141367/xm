package com.zh.dubbo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	/**
	 * Go login
	 * @param
	 * @return
	 */
	@GetMapping("/login/{username}/{password}")
	public String login(@PathVariable String username,@PathVariable String password) {
		System.out.println("username==="+username+"=======password===="+password);
		UsernamePasswordToken upt = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(upt);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "login success";
	}
	
	/**
	 * Exit
	 * @return
	 */
	@RequestMapping("logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/index";
	}
}
