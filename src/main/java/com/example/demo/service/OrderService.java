package com.example.demo.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.Product;

@Service
public class OrderService {

	@Autowired
	private ProductRepository repo;
	@Autowired
	private OrderRepository or;
	@Autowired
	private OrderDetailRepository odr;

	public List<OrderDetail> CarttoList(HashMap<Integer, OrderDetail> hm) {
		List<OrderDetail> list = new ArrayList<OrderDetail>(hm.values());
		return list;
	}

//	public int LatstID_OrderDetail() throws SQLException {
//		String sql = "Select * From orders";
//		PreparedStatement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//		ResultSet rs = stmt.executeQuery(sql);
//		if (rs.next()) {
//			rs.last();
//			int countrow = rs.getInt("id");
//			return countrow;
//		} else {
//			return 0;
//		}
//	}
	
	public int LatstID_OrderDetail() {
		int result;
		try{
			result = or.LatstID_OrderDetail();
		}
		catch(Exception e) {
			result = 0;
		}
		return result;
	}

	@Transactional
	public boolean insertOrder2(Order od) throws SQLException {
		boolean re = true;
		od.setId(LatstID_OrderDetail()+1);
		System.out.println(od.getId());
			for (OrderDetail i : od.getOrderDetails()) {
				i.setOrder(od);
				Product p = i.getProduct();
				p.setCount(i.getProduct().getCount() - i.getCount());
			}
			or.save(od);
			for (OrderDetail i : od.getOrderDetails()) {
				repo.save(i.getProduct());
			}
		return re;
	}

//	public boolean insertOrder(Order od) {
////		long millis = System.currentTimeMillis();
////		java.sql.Date date = new java.sql.Date(millis);
//		boolean result = true;
//		String sqlOrder = "INSERT INTO orders(date_order,user_id,ship_method,approved) values(?,?,?,?)";
//		String sqlOrderDetail = "INSERT INTO orderdetail(count,order_id,product_id) values(?,?,?)";
//		String sqlProduct = "UPDATE products SET count=? WHERE id=?";
//		try {
//			con.setAutoCommit(false);
//			PreparedStatement stmt = con.prepareStatement(sqlOrder);
////			od.setDateOrder(od.getDateOrder());
//			stmt.setDate(1, od.getDateOrder());
//			stmt.setInt(2, od.getUser().getId());
//			stmt.setString(3, od.getShip_method());
//			stmt.setInt(4, od.getApproved());
//			stmt.executeUpdate();
//			con.commit();
//			for (OrderDetail i : od.getOrderDetails()) {
//				stmt = con.prepareStatement(sqlOrderDetail);
//				stmt.setInt(1, i.getCount());
//				stmt.setInt(2, LatstID_OrderDetail());
//				stmt.setInt(3, i.getProduct().getId());
//				stmt.executeUpdate();
//
//				stmt = con.prepareStatement(sqlProduct);
//				stmt.setInt(1, i.getProduct().getCount() - i.getCount());
//				stmt.setInt(2, i.getProduct().getId());
//				stmt.executeUpdate();
//			}
//
//			con.commit();
//		} catch (Exception ex) {
//			try {
//				con.rollback();
//			} catch (SQLException ex1) {
//				Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex1);
//				return false;
//			}
//			result = false;
//			Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
//		} finally {
//			try {
//				con.setAutoCommit(true);
//			} catch (SQLException ex) {
//				Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
//				return false;
//			}
//		}
//		return result;
//	}
}