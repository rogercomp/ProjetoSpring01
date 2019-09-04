package com.iftm.prjreferencia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iftm.prjreferencia.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
