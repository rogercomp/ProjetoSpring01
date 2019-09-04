package com.iftm.prjreferencia.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iftm.prjreferencia.entities.Product;
import com.iftm.prjreferencia.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

	@Autowired
	private ProductService service; 
	
	@GetMapping
	public ResponseEntity<List<Product>> findAll() {
		List<Product> Products = service.findAll();		
		return ResponseEntity.ok().body(Products);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		Product p = service.findById(id);
		return ResponseEntity.ok().body(p);
	}	
}
