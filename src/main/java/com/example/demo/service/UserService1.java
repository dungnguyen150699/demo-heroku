package com.example.demo.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;


@Service
public class UserService1 extends DAO{

	public UserService1() throws SQLException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private UserRepository userRepo;
	
	public User loadUserByUsername(String username) {
		User user = userRepo.findByUsername(username);
//		userRepo.de
		return user;
	}
	
	public User loadUserByUserID(int id) {
		User user = userRepo.findByIDUser(id);
		return user;
	}
	
	public void save(User user) {
		userRepo.save(user);
	}
	
	
	public boolean CheckLogin(User u) {
		boolean result = false;
		try {
			String sql = " Select * from users where username = ? and password=?";
			PreparedStatement p = con.prepareCall(sql);
			p.setString(1, u.getUsername());
			p.setString(2, u.getPassword());
			ResultSet rs = p.executeQuery();
			if (rs.next())
				result = true;
		} catch (SQLException ex) {
			System.err.println(ex.getErrorCode());;
		}
		return result;
	}
}
