package com.iftm.prjreferencia.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iftm.prjreferencia.dto.OrderDTO;
import com.iftm.prjreferencia.dto.UserDTO;
import com.iftm.prjreferencia.entities.Order;
import com.iftm.prjreferencia.entities.User;
import com.iftm.prjreferencia.repositories.OrderRepository;
import com.iftm.prjreferencia.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;	

	
	public List<OrderDTO> findAll() {
		List<Order> list = repository.findAll();
		return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
	}

	public OrderDTO findById(Long id) {
		Optional<Order> obj = repository.findById(id);
		Order entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
		return new OrderDTO(entity);
	}

}
