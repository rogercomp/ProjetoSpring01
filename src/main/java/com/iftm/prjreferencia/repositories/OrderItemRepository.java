package com.iftm.prjreferencia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iftm.prjreferencia.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
