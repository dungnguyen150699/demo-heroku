package com.example.demo.controller.Admin;

import java.lang.ProcessBuilder.Redirect;
import java.net.http.HttpResponse;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Order;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import com.sun.xml.bind.v2.runtime.output.Encoded;


@Controller
public class UserController {
//---------Shop
	@Autowired
	private UserService us;
	@Autowired
	private RoleService rs;
	@Autowired
	private BCryptPasswordEncoder encode;

	@RequestMapping("/register")
	public String register(Model model) {
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
		System.out.println("Wellcom"+user.getUsername());
		
		Set <Role> roles = user.getRoles();
		long count = 0;
		count = roles.stream().filter(role -> role.getName().equals("ADMIN")).count();
		if(count==1) { 
			model.addAttribute("user",user);
			session.setAttribute("user", user);
			return "redirect:/admin/users";
		}
		else {
			model.addAttribute("user",user);
			session.setAttribute("user", user);
			return "redirect:/shop";
		}
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
			Role r = new Role();
			r.setName("USER");
			roles.add(r);
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
		return "SHOP/index";
	}
	
//--------Admin
		
	@RequestMapping(value = "/admin/users")
	public String ManageUser(Model model) {
		model.addAttribute("listUser", us.getAll());
		return "ADMIN/index";
	}
	@RequestMapping(value = "/admin/createAdmin")
	public ResponseEntity<String> createAdmin(Model model) {
		Role r = new Role();
		r.setName("ADMIN");
		Set <Role> listr = new HashSet<Role>();
		listr.add(r);
		
		User u = new User();
		u.setUsername("admin");
		u.setPassword(encode.encode("1"));
		u.setRoles(listr);
		us.save(u);
		return new ResponseEntity<String>("Success",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/admin/users/delete/{id}")
	public String addUser(Model model,@PathVariable int id) {
		us.deleteById(id);
		return "redirect:/admin/users";
	}
}
