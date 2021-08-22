package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
	@Query(value="Insert into orderdetail(count,order_id,product_id) values(?1,?2,?3)",nativeQuery = true)
	boolean insertOrderDetail(int count, int order_id, int product_id);
}
