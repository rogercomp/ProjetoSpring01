package com.iftm.prjreferencia.services;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iftm.prjreferencia.dto.CredentialsDTO;
import com.iftm.prjreferencia.dto.TokenDTO;
import com.iftm.prjreferencia.entities.Order;
import com.iftm.prjreferencia.entities.User;
import com.iftm.prjreferencia.repositories.UserRepository;
import com.iftm.prjreferencia.security.JWTUtil;
import com.iftm.prjreferencia.services.exceptions.JWTAuthenticationException;
import com.iftm.prjreferencia.services.exceptions.JWTAuthorizationException;
import com.iftm.prjreferencia.services.exceptions.ResourceNotFoundException;

@Service
public class AuthService {

	private static final Logger LOG = LoggerFactory.getLogger(AuthService.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	public TokenDTO authenticate(CredentialsDTO dto) {
		try {
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.getEmail(),
					dto.getPassword());
			authenticationManager.authenticate(authToken);
			String token = jwtUtil.generateToken(dto.getEmail());
			return new TokenDTO(dto.getEmail(), token);
		} catch (AuthenticationException e) {
			throw new JWTAuthenticationException("Bad credentials");
		}
	}

	public User authenticated() {
		try {

			UserDetails userdetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return userRepository.findByEmail(userdetails.getUsername());
		} catch (Exception e) {
			throw new JWTAuthorizationException("Acess denied!");
		}
	}
	
	public void validateSelfOrAdmin (Long userId) {
		User user = authenticated();
		if(user == null  || (!user.getId().equals(userId)) && !user.hasRole("ROLE_ADMIN")) {
			throw new JWTAuthorizationException("Acess denied!");
		}
	}
	
	public void validadeOwnOrderOrAdmin (Order order) {
		User user = authenticated();
		if(user == null  || (!user.getId().equals(order.getClient().getId())) && !user.hasRole("ROLE_ADMIN")) {
			throw new JWTAuthorizationException("Acess denied!");
		}
	}
	
	public TokenDTO refreshToken() {
		User user = authenticated();
		return new TokenDTO(user.getEmail(), jwtUtil.generateToken(user.getEmail()));
	}
	
	@Transactional
	public void sendNewPassword(String email) {
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new ResourceNotFoundException("Email not found!");
		}
		
		String newPass = newPassword();
		user.setPassword(passwordEncoder.encode(newPass));
		
		userRepository.save(user);
		
		LOG.info("New password: " + newPass);
		
	}
	
	private String newPassword() {
		char[] vect = new char[10];
		for(int i=0; i<10; i++) {
			vect[i] = randomChar();
		}
		return new String(vect);		
	}
	
	private char randomChar() {
		Random rand = new Random();
		int opt = rand.nextInt(3);
		if(opt == 0) {//generate digit
			return (char) (rand.nextInt(10) + 48);
		}
		else if(opt == 1) {//generate uppercase letter
			return (char) (rand.nextInt(26) + 65);
		}
		else {//generate lowercase letter
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
