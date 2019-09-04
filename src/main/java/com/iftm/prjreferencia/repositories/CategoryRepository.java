package com.iftm.prjreferencia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iftm.prjreferencia.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
