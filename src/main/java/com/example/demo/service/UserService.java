package com.example.demo.service;

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
	
	@Override
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
	
	public void save(User user) {
		userRepo.save(user);
	}
}
