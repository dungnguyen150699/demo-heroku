package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;



@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
//		System.out.println(user.getUsername()+"______"+user.getPassword());
		if(user==null) {
			throw new UsernameNotFoundException("Khong tim thay user!");
		}
		return new UserDetailsImpl(user);
	}
	
	public User findUserByUsername(String username) {
		User user = userRepo.findByUsername(username);
//		userRepo.de
		return user;
	}
	
	public List<User> getAll(){
		List <User> users = userRepo.findAll();
		return users;
	}
	
	public void deleteById(int id) {
		userRepo.deleteById(id);
	}
	
	public void save(User user) {
		userRepo.save(user);
	}
}
