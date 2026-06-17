package com.example.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@RequestMapping("/")
    public String index() {
        return "/index"; // This maps to login
    }
	
	@RequestMapping("/homie")
	@ResponseBody()
	public String hometown() {
		return "this is homie";
	}
	@RequestMapping("/signup")
	@ResponseBody()
	public String signup() {
		return "this is signup";
	}
	
	@RequestMapping("/admin")
	@ResponseBody()
	public String admin() {
		return "this is admin module";
	}
	@RequestMapping("/about")
	@ResponseBody()
		public String aboutUs() {
		return "this is About Us page";
	}
	@GetMapping("/home/{name}/{height}")
	public ModelAndView home(@PathVariable("name") String name, @PathVariable("height") String height) {
		
		
		double height2=Double.parseDouble(height);
		ModelAndView modelAndView = new ModelAndView("ok");
		modelAndView.addObject("message", "Welcome to the Spring Web App!"+height2);
		
		return modelAndView;
	}
	
	

	@GetMapping("/hometwo")
	public ModelAndView hometwo(@RequestParam Map<String,String> ok) {
		ModelAndView mav=new ModelAndView("ok");
		mav.addObject("what",ok.get("pp"));
		return mav;
	}
	
	@GetMapping("/home2")
	public ModelAndView home2(@RequestParam("name") String name, @RequestParam("height") String height) {
		
		
		
		double height2=Double.parseDouble(height);
		ModelAndView modelAndView = new ModelAndView("ok");
		modelAndView.addObject("message", "Welcome to the Spring Web App!"+height2);
		
	
	return modelAndView;
	}
	
	@RequestMapping("/home3")
	public ModelAndView home3(@ModelAttribute("student1") Student stud) {
		
		
		ModelAndView mav=new ModelAndView("ok");
		mav.addObject("message", "Welcome to the Spring Web App!");
		
		return mav;
	}
	
	
	
	
	
	@ModelAttribute
	public void addAttribute(Model model) {
		model.addAttribute("testing123","Hi this is test");
	}
}


class Student{
	
	private String name;
	public String height;
	Student(){}
	public String getName() {return name;}
	public String getHeight() {return height;}
	public void setName(String name) {this.name=name;}
	public void setHeight(String height) {this.height=height;}
	
}

