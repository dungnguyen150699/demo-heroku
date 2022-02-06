package com.example.demo.repository;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.Product;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
	
	@Query(value = "select max(id) from orders", nativeQuery=true)
	int LatstID_OrderDetail();
	
}
