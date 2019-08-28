package com.iftm.prjreferencia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iftm.prjreferencia.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
