package com.example.demo.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "orders")
public class Order implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Date dateOrder;
	private String ship_method;
	private int approved = 0 ;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	public Order() {}
	
	public Order(Integer id, Date dateOrder, String ship_method, int approved, User user,
			List<OrderDetail> orderDetails) {
//		super();
		this.id = id;
		this.dateOrder = dateOrder;
		this.ship_method = ship_method;
		this.approved = approved;
		this.user = user;
		this.orderDetails = orderDetails;
	}


	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Date getDateOrder() {
		return dateOrder;
	}


	public void setDateOrder(Date dateOrder) {
		this.dateOrder = dateOrder;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User object) {
		this.user = object;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}


	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}


	public String getShip_method() {
		return ship_method;
	}


	public void setShip_method(String ship_method) {
		this.ship_method = ship_method;
	}


	public int getApproved() {
		return approved;
	}


	public void setApproved(int approved) {
		this.approved = approved;
	}
	
	
}
