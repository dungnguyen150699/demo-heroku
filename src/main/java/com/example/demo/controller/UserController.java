package com.example.demo.controller;

import java.lang.ProcessBuilder.Redirect;
import java.security.Principal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Order;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.example.demo.service.UserService1;

@Controller
public class UserController {

	@Autowired
	private UserService us;
	@Autowired
	private RoleService rs;
	@Autowired
	private BCryptPasswordEncoder encode;

	@RequestMapping("/register")
	public String search(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "RegisterForm";
	}

	@RequestMapping("/login")
	public String loginView(Model model,HttpSession session) {
//		model.addAttribute("user", new User());
		session.removeAttribute("user");
		return "LoginForm";
	}

	@RequestMapping(value = "/dologin")
	public String doLogin(HttpSession session, Model model) {
		User user = us.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		System.out.println(user.getUsername()+"__________hihi");
		model.addAttribute("user",user);
		session.setAttribute("user", user);
		return "redirect:/shop";
	}

	@RequestMapping(value = "/doRegister")
	public String doRegister(Model model, @ModelAttribute("user") User user) {
		User u = null;
		u = us.findUserByUsername(user.getUsername());
		if (u != null) {
			String str = "User has exist";
			model.addAttribute("meg", str);
			return "RegisterForm";
		} else {
			String str = "Congratulations on your successful registration";
			Set<Role> roles = new HashSet();
			System.out.println(rs.getRole("USER").getNameRole());
			roles.add(rs.getRole("USER"));
//			-----------
			user.setRoles(roles);
			user.setPassword(encode.encode(user.getPassword()));
			us.save(user);
			model.addAttribute("meg", str);
			return "RegisterForm";
		}
	}

	@RequestMapping(value = "/home")
	public String home(Model model) {
		return "index";
	}

}
