package com.iftm.prjreferencia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iftm.prjreferencia.entities.Order;
import com.iftm.prjreferencia.entities.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

	    List<Order> findByClient(User client); 
}
