package br.com.igrejadecristo.folhetodigital.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.dto.ContatoDTO;

@Service
public class ContatoService {

	@Autowired
	private EmailService emailService;
	
	public void enviarContato(ContatoDTO contato) {
		emailService.sendContatoHtml(contato);
	}
}
