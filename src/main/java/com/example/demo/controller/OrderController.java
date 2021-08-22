package com.example.demo.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;

@Controller
public class OrderController {

	@Autowired
	private OrderService os;

	@Autowired
	public JavaMailSender emailSender;

	private Order order;

	private static final String FRIEND_EMAIL = "tiendungac99@gmail.com";

	@RequestMapping("/cart")
	public String order(Model model, HttpSession session) throws SQLException {
		HashMap<Integer, OrderDetail> orderDetail = (HashMap<Integer, OrderDetail>) session.getAttribute("listCart");
		if (orderDetail == null) {
			orderDetail = new HashMap<Integer, OrderDetail>();
			session.setAttribute("listCart", orderDetail);
			model.addAttribute("meg", "you havenot order!");
		}
//		os = new OrderService();
		order = new Order();
		order.setOrderDetails(os.CarttoList(orderDetail));
		model.addAttribute("order", order);

		double price = 0;
		for (OrderDetail i : orderDetail.values()) {
			double priceitem = i.getCount() * i.getProduct().getPrice();
			price += priceitem;
		}
		model.addAttribute("totalPrice", price);
		return "cart";
	}

	@RequestMapping("/updatecart")
	public String updateorder(Model model, @ModelAttribute("order") Order order, HttpSession session)
			throws SQLException {
		HashMap<Integer, OrderDetail> orderDetail = (HashMap<Integer, OrderDetail>) session.getAttribute("listCart");
//		os = new OrderService();
		this.order = new Order();
		this.order.setOrderDetails(os.CarttoList(orderDetail));
		double price = 0;
		int j = 0;
		for (OrderDetail i : order.getOrderDetails()) {
			double priceitem = i.getCount() * this.order.getOrderDetails().get(j).getProduct().getPrice();
			orderDetail.get(this.order.getOrderDetails().get(j).getProduct().getId()).setCount(i.getCount());
			this.order.getOrderDetails().get(j).setCount(i.getCount());
			price += priceitem;
			j++;
		}
		session.setAttribute("listCart", orderDetail);
		session.setAttribute("totalPrice", price);
		model.addAttribute("order", this.order);
		model.addAttribute("totalPrice", price);
		return "cart";
	}

	@RequestMapping("/delete_item/{id}")
	public String deleteItem(Model model, @PathVariable(name = "id") int id, HttpSession session) throws SQLException {
		HashMap<Integer, OrderDetail> listCart = (HashMap<Integer, OrderDetail>) session.getAttribute("listCart");
		listCart.remove(id);
//		os = new OrderService();
		this.order.setOrderDetails(os.CarttoList(listCart));
		session.setAttribute("listCart", listCart);
		model.addAttribute("order", listCart);
		return "redirect:/cart";
	}

	@RequestMapping("/checkout")
	public String checkout(Model model, HttpSession session) {
		String str = "";
		model.addAttribute("ship_method", str);
		User u = (User) session.getAttribute("user");
		HashMap<Integer, OrderDetail> orderDetail = (HashMap<Integer, OrderDetail>) session.getAttribute("listCart");
		if (orderDetail.size() > 0) {
			if (u.getId() == 0) {
				return "redirect:/login";
			} else {
				Order order = new Order();
				model.addAttribute("order", order);
				return "checkout";
			}
		} else
			return "redirect:/cart";

	}

	@RequestMapping("/approved_order")
	public String approved_order(Model model, @ModelAttribute("order") Order od, HttpSession session)
			throws SQLException {
		User u = (User) session.getAttribute("user");
		HashMap<Integer, OrderDetail> orderDetail = (HashMap<Integer, OrderDetail>) session.getAttribute("listCart");
		order = new Order();
		order.setUser((User) session.getAttribute("user"));
		order.setShip_method(od.getShip_method());
		order.setOrderDetails(os.CarttoList(orderDetail));

		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		order.setDateOrder(date);
//		System.out.println("Service os if == null u wrong______"+ os);

		if (os.insertOrder2(order)) {
			SimpleMailMessage message = new SimpleMailMessage();

			message.setTo(FRIEND_EMAIL);
			message.setSubject("Test Simple Email");
			message.setText("Hello, Im testing Simple Email");
			this.emailSender.send(message);
			return "checkout_done";
		}
		return "redirect:/shop";
	}
}
