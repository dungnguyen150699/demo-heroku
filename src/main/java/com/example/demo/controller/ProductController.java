package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService1;

@Controller
public class ProductController {

	@Autowired
	private ProductService ps;

	@RequestMapping(value = { "/shop"})
	public String shop(Model model, HttpSession session) {
		return showPage(model, session, 1, "asc");
	}

	@RequestMapping(value = "/page/{pageNum}")
	public String showPage(Model model, HttpSession session, @PathVariable(name = "pageNum") int currentPage,
			@Param("sortDir") String sortDir) {
//		Pageable firstPageWithTwoElements = PageRequest.of(currentPage, currentPage);
		User u = (User) session.getAttribute("user");
		if (u == null) {
			model.addAttribute("user", new User());
			session.setAttribute("user", new User());
		} else {
			model.addAttribute("user", u);
		}
//		------------
		Page<Product> pageProducts = ps.listAll(currentPage, "price", sortDir);
		List<Product> listProducts = pageProducts.getContent();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", pageProducts.getTotalPages());
		model.addAttribute("totalItems", pageProducts.getTotalElements());
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("listProduct", listProducts);
		return "shop";
	}

	@RequestMapping("/search")
	public String search(Model model, @RequestParam(name = "search") String search) {
		List<Product> listProduct = ps.findByNameLike(search);
		model.addAttribute("listProduct", listProduct);
		return "shop";
	}

	@RequestMapping("/addcart/{id}")
	public String addcart(Model model, @PathVariable(name = "id") int id, HttpSession session) {
		Product pd = ps.findbyID(id);
		OrderDetail od = new OrderDetail();
		od.setProduct(pd);
		HashMap<Integer, OrderDetail> listCart = (HashMap<Integer, OrderDetail>) session.getAttribute("listCart");
		if (listCart == null) {
			listCart = new HashMap<Integer, OrderDetail>();
			listCart.put(id, od);
			session.setAttribute("listCart", listCart);
		} else {
			listCart.put(id, od);
			session.setAttribute("listCart", listCart);
		}
		return "redirect:/shop";
	}

}
