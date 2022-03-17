package br.com.pic.folheto.controllers;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.pic.folheto.dto.EmailDTO;
import br.com.pic.folheto.dto.NewPasswordDTO;
import br.com.pic.folheto.security.JWTUtil;
import br.com.pic.folheto.security.UserSS;
import br.com.pic.folheto.services.AuthService;
import br.com.pic.folheto.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	@Operation(summary = "Refresh Token", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername(), user.getAuthorities());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	@Operation(summary = "Forgot", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
		service.sendNewPassword(objDto.getEmail());
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot/newPassword", method = RequestMethod.POST)
	@Operation(summary = "New Password", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<Void> forgotNewPassword(@Valid @RequestBody NewPasswordDTO objDto) {
		service.trocaSenha(objDto);
		return ResponseEntity.noContent().build();
	}
}
