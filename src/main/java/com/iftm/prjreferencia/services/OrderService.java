package com.iftm.prjreferencia.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iftm.prjreferencia.dto.CategoryDTO;
import com.iftm.prjreferencia.dto.OrderDTO;
import com.iftm.prjreferencia.dto.OrderItemDTO;
import com.iftm.prjreferencia.entities.Category;
import com.iftm.prjreferencia.entities.Order;
import com.iftm.prjreferencia.entities.OrderItem;
import com.iftm.prjreferencia.entities.Product;
import com.iftm.prjreferencia.entities.User;
import com.iftm.prjreferencia.entities.enums.OrderStatus;
import com.iftm.prjreferencia.repositories.OrderItemRepository;
import com.iftm.prjreferencia.repositories.OrderRepository;
import com.iftm.prjreferencia.repositories.ProductRepository;
import com.iftm.prjreferencia.repositories.UserRepository;
import com.iftm.prjreferencia.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private AuthService authService;

	public List<OrderDTO> findAll() {
		List<Order> list = repository.findAll();
		return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
	}

	public OrderDTO findById(Long id) {
		Optional<Order> obj = repository.findById(id);
		Order entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
		authService.validadeOwnOrderOrAdmin(entity);
		return new OrderDTO(entity);
	}

	@Transactional(readOnly = true)
	public List<OrderItemDTO> findItems(Long id) {
		Order order = repository.getOne(id);
		authService.validadeOwnOrderOrAdmin(order);
		Set<OrderItem> set = order.getItems();
		return set.stream().map(e -> new OrderItemDTO(e)).collect(Collectors.toList());
	}

	public List<OrderDTO> findByClient() {
		User client = authService.authenticated();
		List<Order> list = repository.findByClient(client);
		return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public List<OrderDTO> findByClientId(Long clientId) {
		User client = userRepository.getOne(clientId);
		List<Order> list = repository.findByClient(client);
		return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
	}

	@Transactional
	public OrderDTO placeOrder(List<OrderItemDTO> dto) {
	    User client = authService.authenticated();
	    Order order = new Order(null, Instant.now(), OrderStatus.WAITING_PAYMENT, client);
	    
	    for(OrderItemDTO itemDTO : dto) {
	    	Product product = productRepository.getOne(itemDTO.getProductId());
	    	OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), itemDTO.getPrice());
	    	order.getItems().add(item);
	    }
	    
	    repository.save(order);
	    orderItemRepository.saveAll(order.getItems());
	    
	    return new OrderDTO(order);
	}
	
	@Transactional
	public OrderDTO update(Long id, OrderDTO dto) {
		try {
			Order entity = repository.getOne(id);
			updateData(entity, dto);
			entity = repository.save(entity);
			return new OrderDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}

	}

	private void updateData(Order entity, OrderDTO dto) {
		entity.setOrderStatus(dto.getOrderStatus());
	}
}
