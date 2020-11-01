package br.com.igrejadecristo.folhetodigital.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.igrejadecristo.folhetodigital.dto.ContatoDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Membro;

public interface EmailService {

	void sendContato(ContatoDTO contato);
	
	void sendEmail(SimpleMailMessage msg);

	void sendContatoHtml(ContatoDTO contato);
	
	void sendNewPasswordEmail(Membro membro, String newPassWord);
	
	void sendNewPassWord(Membro membro, String senha);
	
	void sendEmailHtml(MimeMessage msg);
}
