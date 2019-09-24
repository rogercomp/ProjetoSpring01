package com.iftm.prjreferencia.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iftm.prjreferencia.dto.ProductDTO;
import com.iftm.prjreferencia.dto.UserDTO;
import com.iftm.prjreferencia.dto.UserInsertDTO;
import com.iftm.prjreferencia.entities.Product;
import com.iftm.prjreferencia.entities.User;
import com.iftm.prjreferencia.repositories.ProductRepository;
import com.iftm.prjreferencia.services.exceptions.DatabaseException;
import com.iftm.prjreferencia.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	public List<ProductDTO> findAll() {
		List<Product> list = repository.findAll();
		return list.stream().map(e -> new ProductDTO(e)).collect(Collectors.toList());
	}

	public ProductDTO findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
		return new ProductDTO(entity);
	}
	
	public ProductDTO insert(ProductDTO dto) {
		Product entity = dto.toEntity();
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = repository.getOne(id);
			updateData(entity, dto);
			entity =  repository.save(entity);
			return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}

	}

	private void updateData(Product entity, ProductDTO dto) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgURL(dto.getImgURL());
	}
}
