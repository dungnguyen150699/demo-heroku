package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUsername(String username);
	
	@Query(value = "select * from users where id = ?1", nativeQuery=true)
	public User findByIDUser(int id);
	
	@Query(value = "Delete from users where id = :id", nativeQuery=true)
	public void deleteById(@Param(value = "id") int id);
}