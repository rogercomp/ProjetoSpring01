package com.iftm.prjreferencia.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iftm.prjreferencia.dto.CredentialsDTO;
import com.iftm.prjreferencia.dto.TokenDTO;
import com.iftm.prjreferencia.services.AuthService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private AuthService service; 
	
	@PostMapping("/login")
	public ResponseEntity<TokenDTO> login(@RequestBody CredentialsDTO dto) {
		TokenDTO tokenDTO = service.authenticate(dto);	
		return ResponseEntity.ok().body(tokenDTO);
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<TokenDTO> refresh() {
		TokenDTO tokenDTO = service.refreshToken();	
		return ResponseEntity.ok().body(tokenDTO);
	}
}
