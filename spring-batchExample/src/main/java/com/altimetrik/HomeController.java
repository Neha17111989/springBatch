package com.altimetrik;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping
	@RequestMapping("/")
	public String landingPage(){
		return "<h1> Welcome</h1>";
	}
	
	@GetMapping
	@RequestMapping("/user")
	public String userPage() {
		return "<h1> Welcome User!!</h1>";
	}
	
	@GetMapping
	@RequestMapping("/admin")
	public String adminPage() {
		return "<h1> Welcome Admin!!</h1>";
	}
}
