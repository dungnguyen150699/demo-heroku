package com.example.demo.controller.Admin;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Product;
import com.example.demo.entity.Productdto;
import com.example.demo.entity.User;
import com.example.demo.service.ProductService;


@Controller
@RequestMapping(value="/admin/product")
public class MProductController {
	@Autowired ProductService ps;
	private static Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
	
	@RequestMapping(value="")
	public String manageProduct(Model model) {
		model.addAttribute("listProduct", ps.findAllProductDesc());
		return "ADMIN/product";
	}
	
	@RequestMapping(value="/insertForm")
	public String insertForm(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("mes", "insert");
		return "ADMIN/formProduct";
	}
	@RequestMapping(value="/updateForm")
	public String formProduct(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("mes", "update");
		return "ADMIN/formProduct";
	}
	
	@RequestMapping(value = "/addProduct")
	public String addProduct(Model model,@ModelAttribute("product") Productdto product) {
		model.addAttribute("message", "Upload success");
		System.out.println("----------");
		try {
			MultipartFile myFile = product.getImg();
			String fileName = myFile.getOriginalFilename();
			String typeFile = myFile.getContentType();
			File file = new File(this.getFolderUpload(), fileName);
			myFile.transferTo(file);
			Product product_entity = new Product();
			product_entity.setCount(product.getCount());
			product_entity.setName(product.getName());
			product_entity.setPrice(product.getPrice());
			product_entity.setImg("/images/"+fileName);
			ps.saveProduct(product_entity);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "Upload failed");
		}
		return "redirect:/admin/product";
	}
	
	public File getFolderUpload() {
		Path staticPath = Paths.get("src\\main\\resources\\static\\images");
//        Path imagePath = Paths.get("img");
		Path CURRENT_FOLDER1 = CURRENT_FOLDER.resolve(staticPath); //Để cho nó ko bị lặp
		System.out.println(CURRENT_FOLDER1 + "______________2__");
		File folderUpload = new File(CURRENT_FOLDER1.toString());
		if (!folderUpload.exists()) {
			folderUpload.mkdirs();
		}
		return folderUpload;
	}
}
