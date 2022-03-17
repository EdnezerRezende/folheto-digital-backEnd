package br.com.pic.folheto.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(value="/administracao")
public class AdministracaoController {


	@PreAuthorize("hasAnyRole('ADMIN')")
	@Operation(summary = "Obter Logs do Servidor", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(value = "/logs",method=RequestMethod.GET)
	public ResponseEntity<byte[]> downloadLogServidor() throws IOException {

		final byte[] arquivo = Files.readAllBytes( Paths.get("./logs/logs_pic.log") );

		final String labelFile = "Log_PIC_"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss")) + ".txt";
		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.add("content-disposition", "attachment;filename=" +labelFile);
		httpHeaders.add("content-type", "text/plain");

		HttpEntity<byte[]> entity = new HttpEntity<byte[]>( arquivo, httpHeaders);

		return ResponseEntity.ok().headers(httpHeaders).body(entity.getBody());
	}


	
}
