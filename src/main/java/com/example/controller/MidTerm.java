package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@Controller
@RequestMapping("/test")
public class MidTerm{
	
	@RequestMapping("1")
	@ResponseBody()
	public String what() {
		return "123";
	}
	
	@GetMapping("/2")
	public ModelAndView test() {
		ModelAndView mav=new ModelAndView("number2");
		ArrayList<String> al=new ArrayList<String>();
		al.add("a");
		al.add("b");
		al.add("c");
		mav.addObject("test",1);
		mav.addObject("list",al);
		return mav;
	}
	
}
