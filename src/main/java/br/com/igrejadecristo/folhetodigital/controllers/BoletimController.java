package br.com.igrejadecristo.folhetodigital.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.igrejadecristo.folhetodigital.services.BoletimService;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping(value="/boletins")
public class BoletimController {

	@Autowired
	private BoletimService boletimService;
	
	@RequestMapping(value="/semanal/{idIgreja}", method = RequestMethod.GET)
	public ResponseEntity<Void> buscaPorId(@PathVariable Integer idIgreja, HttpServletResponse response){
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
		
		return ResponseEntity.noContent().build();
	}

}
