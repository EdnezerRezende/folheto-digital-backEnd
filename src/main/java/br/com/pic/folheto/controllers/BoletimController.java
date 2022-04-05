package br.com.pic.folheto.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.pic.folheto.services.BoletimService;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping(value="/boletins")
public class BoletimController {

	@Autowired
	private BoletimService boletimService;
	
	@RequestMapping(value="/semanal/{idIgreja}", method = RequestMethod.GET)
	@Operation(summary = "Gerar Boletim semanal", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<Void> buscaPorId(@PathVariable final Integer idIgreja, final HttpServletResponse response){
		try {
			boletimService.gerarBoletimSemanal(idIgreja, response);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok().build();
	}

}
