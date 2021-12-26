package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Productdto implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private MultipartFile img;
	private double price;
	private int count;

}
