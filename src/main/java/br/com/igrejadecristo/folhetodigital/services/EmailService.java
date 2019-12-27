package br.com.igrejadecristo.folhetodigital.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.igrejadecristo.folhetodigital.dto.ContatoDTO;

public interface EmailService {

	void sendContato(ContatoDTO contato);
	
	void sendEmail(SimpleMailMessage msg);
	
}
