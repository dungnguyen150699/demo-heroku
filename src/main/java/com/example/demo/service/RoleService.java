package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;


@Service
public class RoleService {
	@Autowired RoleRepository repo;
	
	public Role getRole(String name) {
		return repo.findByName(name);
	}
}
