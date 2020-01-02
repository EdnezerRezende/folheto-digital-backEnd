package br.com.igrejadecristo.folhetodigital.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.igrejadecristo.folhetodigital.dto.ContatoDTO;

public interface EmailService {

	void sendContato(ContatoDTO contato);
	
	void sendEmail(SimpleMailMessage msg);

	void sendContatoHtml(ContatoDTO contato);
	
	void sendEmailHtml(MimeMessage msg);
}
