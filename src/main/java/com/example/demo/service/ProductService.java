package com.example.demo.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

@Service
public class ProductService {

	public ProductService(){
	}

	@Autowired
	private ProductRepository productRepo;
	
	public Page<Product> listAll(int pageNum, String sortField, String sortDir){
		int pageSize = 8;
		
		Pageable pageable = PageRequest.of(pageNum-1, pageSize,
				sortDir.equals("asc") ? Sort.by(sortField).ascending():
					Sort.by(sortField).descending());
		
		return productRepo.findAll(pageable);
	}
	
	public List<Product> findAllProductDesc() {
		List <Product> listProduct = productRepo.findAllProductDesc();
		return listProduct;
	}
	
	public List<Product> findByNameLike(String nameProduct){
		List <Product> listProduct = productRepo.findByNameLike(nameProduct);
		return listProduct;
	}
	
	public Product findbyID(int id) {
		Product pd = productRepo.findByID(id);
		return pd;
	}
	
	public void saveProduct(Product p) {
		productRepo.save(p);
	}
}
